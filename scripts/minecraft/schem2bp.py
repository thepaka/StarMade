#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
@author: thepaka
@version: v0.1
"""

import os
import sys
import time

import resolver
import expander
import blocks
from blocks import Blocks

import nbt
from nbt import nbt
from nbt.nbt import *

sys.path.append('..')
import blueprint
from blueprint import blueprint
from blueprint import binary



def expand(b, size):

    if size == 0:
        return

    print "Expanding , factor %d" % size
    ex = expander.Expander()
    b.expand(size)

    for z in xrange(0, b.length, size):
        for y in xrange(0, b.height, size):
            for x in xrange(0, b.width, size):
                pos = (x, y, z)
                id, data = b.get( pos, True )

                ex.init(size, id, data)

                for z2 in xrange(0, size):
                    for y2 in xrange(0, size):
                        for x2 in xrange(0, size):
                            id2, data2 = ex.get( (x2, y2, z2 ) )
                            pos2 = ( x+x2, y+y2, z+z2 )
                            b.add( pos2, id2, data2, True )


def schem2bp(fileName, opt_expand = False):

    # Load NBT

    nbtfile = nbt.NBTFile(fileName, 'rb')

    # Compute values

    height = nbtfile['Height'].value
    length = nbtfile['Length'].value
    width = nbtfile['Width'].value

    b = Blocks(width, height, length)
    b.datas = nbtfile['Data'].value
    b.ids = nbtfile['Blocks'].value

    # Expand
    if opt_expand:
        expand(b, 2)

    # The ship core will be the bedrock block (id 7)
    b.set_orig_on_blockid(7)

    pos = b.index_to_pos(len(b.datas)-1)
    print "Last block : x=%2d, y=%2d, z=%2d" % pos

    datas = {}
    blocks_counter = {}
    for i in xrange(0, len(b.datas)):
        pos = b.index_to_pos(i)
        id, data = b.get(pos)

        if id:
            chunk_pos = ( pos[0] / 16 * 16,  pos[1] / 16 * 16,  pos[2] / 16 * 16 )

            data_pos = ( (chunk_pos[0] + 128) / 255 ,
                         (chunk_pos[1] + 128) / 255 ,
                         (chunk_pos[2] + 128) / 255 )

            if not data_pos in datas:
                print "New data (%2d, %2d, %2d)" % data_pos
                datas[data_pos] = {}

            if not chunk_pos in datas[data_pos]:
                print "New chunk (%2d, %2d, %2d)" % chunk_pos
                datas[data_pos][chunk_pos] = {}

            x_off = chunk_pos[0]
            y_off = chunk_pos[1]
            z_off = chunk_pos[2]

            id, hp, active, orient = resolver.minecraft_to_starmade( id, data )

            block_pos = (pos[0] - x_off, pos[1] - y_off, pos[2] - z_off)
            datas[data_pos][chunk_pos][block_pos] = {
                'id': id,
                'hp': hp,
                'active': active,
                'orient': orient
            }

            if not id in blocks_counter:
                blocks_counter[id]=1
            else:
                blocks_counter[id]+=1

    # Create blueprint

    basename = os.path.basename(fileName)
    bpname = os.path.splitext(basename)[0]

    bp = {
        'datas': {},
        'header': {
                'blocks': blocks_counter,
                'bounds_a': ( b.orig[0] - 1.0,
                              b.orig[1] - 1.0,
                              b.orig[2] - 1.0),
                'bounds_b': ( b.orig[0] + b.width + 0.0,
                              b.orig[1] + b.height + 0.0,
                              b.orig[2] + b.length + 0.0),
                'int_a': 0,
                'int_b': 0},
        'logic': {'controllers': [], 'int_a': 0},
        'meta': {'byte_a': 1, 'int_a': 0}
    }

    for data in datas:

        data_filename = "ENTITY_SHIP_%s.%d.%d.%d.smd2" %  ((bpname, ) + data)

        bp_d = bp['datas'][data_filename] = {
                'chunk_index': {},
                'chunk_timestamps': {},
                'chunks': [],
                'filelen': 0,
                'int_a': 0,
                'pos': data
        }

        ci = 0
        for chunk in datas[data]:

            pos_index = blueprint.indexed_pos( chunk, data )

            bp_d['chunk_index'][pos_index] = {
                'id' : ci,
                'len' : 0
            }
            bp_d['chunk_timestamps'][pos_index] = int(time.time()* 1000)
            bp_d['chunks'].append({
                'blocks': datas[data][chunk],
                'pos': chunk,
                'timestamp': int(time.time()* 1000),
                'type': 1
            })
            ci += 1

    blueprint.writeBlueprint(bpname, bp)


if __name__ == '__main__':

    argc = len(sys.argv)

    fileName = sys.argv[1]
    if not os.path.exists(fileName):
       raise Exception('%s is not an existing file' % fileName)

    opt_expand = False
    if argc == 3:
        if sys.argv[2] == "expand":
            opt_expand = True
    
    schem2bp(fileName, opt_expand)
