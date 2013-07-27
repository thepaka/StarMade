#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
@author: thepaka
@version: v0.1
"""

import os
import sys

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


def main(dirName):

    # Load blueprint

    bp = blueprint.readBlueprint(dirName)

    # Compute values

    width =  int( abs(bp['header']['bounds_a'][0] - bp['header']['bounds_b'][0] ) - 1 )
    height = int( abs(bp['header']['bounds_a'][1] - bp['header']['bounds_b'][1] ) - 1 )
    length = int( abs(bp['header']['bounds_a'][2] - bp['header']['bounds_b'][2] ) - 1 )

    orig = ( bp['header']['bounds_a'][0] + 1,
             bp['header']['bounds_a'][1] + 1,
             bp['header']['bounds_a'][2] + 1)

    b = Blocks(width, height, length, orig)

    for data in bp['datas'].values():
        chunks = data['chunks']
        chunk_index = data['chunk_index']
        data_pos = data['pos']

        for chunk in chunks:
            x_off = chunk['pos'][0]
            y_off = chunk['pos'][1]
            z_off = chunk['pos'][2]

            if not (x_off - data_pos[0] * 256,
                    y_off - data_pos[1] * 256,
                    z_off - data_pos[2] * 256 ) in chunk_index:
               print "Ignored Chunk pos : x=%d y=%d z=%d" % ( x_off, y_off, z_off )
               continue

            print "Chunk pos : x=%d y=%d z=%d" % ( x_off, y_off, z_off )

            for pos, data in chunk['blocks'].items():
                block_info = resolver.starmade_to_minecraft( data['id'], data['orient'] )
                block_pos = (pos[0] + x_off, pos[1] + y_off , pos[2] + z_off)

                b.add(block_pos, block_info[0], block_info[1])

    # Create NBT

    nbtfile = nbt.NBTFile()
    nbtfile.name = "Schematic"

    nbtfile.tags.extend([
        TAG_String(name='Materials', value = 'Alpha'),

        TAG_List(name='Entities', type=TAG_Compound),
        TAG_List(name='TileEntities', type=TAG_Compound),

        TAG_Byte_Array(name='Data'),
        TAG_Byte_Array(name='Blocks'),

        TAG_Int(name='WEOffsetX', value = 0),
        TAG_Int(name='WEOffsetY', value = 0),
        TAG_Int(name='WEOffsetZ', value = 0),

        TAG_Int(name='WEOriginX', value = 0),
        TAG_Int(name='WEOriginY', value = 0),
        TAG_Int(name='WEOriginZ', value = 0),

        TAG_Short(name='Height', value = height),
        TAG_Short(name='Length', value = length),
        TAG_Short(name='Width', value = width)
    ])

    nbtfile['Data'].value = b.datas
    nbtfile['Blocks'].value = b.ids

    outfile = "%s.schematic" % os.path.basename(dirName)
    print 'Writing %s' % outfile
    nbtfile.write_file(outfile)

if __name__ == '__main__':

    dirName = sys.argv[1]
    if not os.path.isdir(dirName):
       raise Exception('%s is not a directory' % dirName)

    main(dirName)
