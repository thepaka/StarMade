# -*- coding: utf-8 -*-
"""
@author: thepaka
@version: v0.1
"""

class Blocks:

    def __init__(self, width, height, length, orig):

        print "Blocks : width=%d height=%d length=%d " % ( width, height, length )
        print "       : orig_x=%d orig_y=%d orig_z=%d " % ( orig[0], orig[1], orig[2])

        self.height = height
        self.length = length
        self.width = width

        self.orig = orig

        self.ids = bytearray( width * height * length )
        self.datas = bytearray( width * height * length )

    def add(self, pos, id, data=0):

        index = self.pos_to_index( pos )

        #print "Add block : x=%2d y=%2d z=%2d index=%3d"\
        #      "\t id=%3d data=%2d" % ( pos + (index, id, data) )

        self.ids[index] = id
        self.datas[index] = data

    def get(self, pos):

        index = self.pos_to_index( pos )

        id = self.ids[index]
        data = self.datas[index]

        #print "Get block : x=%2d y=%2d z=%2d index=%3d"\
        #      "\t id=%3d data=%2d" % ( pos + (index, id, data )

        return id, data

    def pos_to_index(self, pos):

        y_size = self.length * self.width
        z_size = self.width

        x = pos[0] - 8 - self.orig[0]
        y = pos[1] - 8 - self.orig[1]
        z = pos[2] - 8 - self.orig[2]

        return int( x + z * z_size + y * y_size )

    def index_to_pos(self, index):

        y_size = self.length * self.width
        z_size = self.width

        y = int( index / y_size )
        z = int( ( index - y * y_size ) / z_size )
        x = int( ( index - y * y_size ) - z * z_size )

        x += 8 + self.orig[0]
        y += 8 + self.orig[1]
        z += 8 + self.orig[2]

        return ( x, y, z )
