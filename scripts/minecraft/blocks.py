# -*- coding: utf-8 -*-
"""
@author: thepaka
@version: v0.1
"""

class Blocks:

    def __init__(self, width, height, length, orig_x, orig_y, orig_z):

        print "Blocks : width=%d height=%d length=%d " % ( width, height, length )
        print "       : orig_x=%d orig_y=%d orig_z=%d " % ( orig_x, orig_y, orig_z )

        self.height = height
        self.length = length
        self.width = width

        self.orig = [ orig_x, orig_y, orig_z ]

        self.ids = bytearray( width * height * length )
        self.datas = bytearray( width * height * length )

    def add(self, x, y, z, id, data=0):

        _x = x - 8 - self.orig[0]
        _y = y - 8 - self.orig[1]
        _z = z - 8 - self.orig[2]

        index = int(_x + _z * self.width + _y * ( self.length * self.width ))

        #print "Add block : x=%2d y=%2d z=%2d index=%3d\t _x=%2d _y=%2d _z=%2d"\
        #      "\t id=%3d data=%2d" % ( x, y, z, index, _x, _y, _z, id, data )

        self.ids[index] = id
        self.datas[index] = data

