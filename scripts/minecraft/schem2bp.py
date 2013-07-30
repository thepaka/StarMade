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
import blocks
from blocks import Blocks

import nbt
from nbt import nbt
from nbt.nbt import *

sys.path.append('..')
import blueprint
from blueprint import blueprint
from blueprint import binary


def schem2bp(fileName):

    # Load NBT

    nbtfile = nbt.NBTFile(fileName, 'rb')

    # Compute values

    height = nbtfile['Height'].value
    length = nbtfile['Length'].value
    width = nbtfile['Width'].value

    b = Blocks(width, height, length)
    b.datas = nbtfile['Data'].value
    b.ids = nbtfile['Blocks'].value

    # The ship core will be the bedrock block (id 7)
    b.set_orig_on_blockid(7)

    pos = b.index_to_pos(len(b.datas)-1)
    print "Last block : x=%2d, y=%2d, z=%2d" % pos

    datas = {}
    blocks_counter = {}
    for i in range(0, len(b.datas)):
        pos = b.index_to_pos(i)
        id, data = b.get(pos)

        if id:
            chunk_pos = ( pos[0] / 16 * 16,  pos[1] / 16 * 16,  pos[2] / 16 * 16 )

            data_pos = ( (chunk_pos[0] + 128) / 256 * 256,
                         (chunk_pos[1] + 128) / 256 * 256,
                         (chunk_pos[2] + 128) / 256 * 256)

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
            bp_d['chunk_index'][chunk] = {
                'id' : ci,
                'len' : 0
            }
            bp_d['chunk_timestamps'][chunk] = int(time.time()* 1000)
            bp_d['chunks'].append({
                'blocks': datas[data][chunk],
                'pos': chunk,
                'timestamp': int(time.time()* 1000),
                'type': 1
            })
            ci += 1

    blueprint.writeBlueprint(bpname, bp)


if __name__ == '__main__':

    fileName = sys.argv[1]
    if not os.path.exists(fileName):
       raise Exception('%s is not an existing file' % fileName)

    schem2bp(fileName)
