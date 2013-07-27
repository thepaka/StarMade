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

        _x = pos[0] - 8 - self.orig[0]
        _y = pos[1] - 8 - self.orig[1]
        _z = pos[2] - 8 - self.orig[2]

        index = int(_x + _z * self.width + _y * ( self.length * self.width ))

        #print "Add block : x=%2d y=%2d z=%2d index=%3d\t _x=%2d _y=%2d _z=%2d"\
        #      "\t id=%3d data=%2d" % ( x, y, z, index, _x, _y, _z, id, data )

        self.ids[index] = id
        self.datas[index] = data

