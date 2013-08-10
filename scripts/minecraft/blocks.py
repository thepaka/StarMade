# -*- coding: utf-8 -*-
"""
@author: thepaka
@version: v0.1
"""

class Blocks:

    def __init__(self, width, height, length, orig=(0,0,0)):

        print "Blocks : width=%d height=%d length=%d " % ( width, height, length )
        print "       : orig_x=%d orig_y=%d orig_z=%d " % orig

        self.height = height
        self.length = length
        self.width = width

        self.orig = orig

        self.ids = bytearray( width * height * length )
        self.datas = bytearray( width * height * length )

    def add(self, pos, id, data=0, raw_pos = False):

        index = self.pos_to_index( pos, raw_pos )

        #print "Add block : x=%2d y=%2d z=%2d index=%3d"\
        #      "\t id=%3d data=%2d" % ( pos + (index, id, data) )

        self.ids[index] = id
        self.datas[index] = data

    def get(self, pos, raw_pos = False):

        index = self.pos_to_index( pos, raw_pos )

        id = self.ids[index]
        data = self.datas[index]

        #print "Get block : x=%2d y=%2d z=%2d index=%3d"\
        #      "\t id=%3d data=%2d" % ( pos + (index, id, data) )

        return id, data

    def set_orig_on_blockid(self, block_id):

        print "Finding orig block ..."
        index = -1
        for i in xrange(0, len(self.ids)):
            if self.ids[i] == block_id:
                if index == -1:
                    index = i
                else:
                    print "Error. Untable to find the ship core block"
                    print "Minecraft block ID %d is not uniq" % block_id
                    exit(1)
        if index == -1:
            print "Error. Unable to find the ship core block"
            print "Minecraft block ID %d not found" % block_id
            exit(1)

        x, y, z = self.index_to_pos(index, True)
        self.orig = ( -x, -y, -z )

        print "Minecraft block ID %d found at index %d" % (block_id, index)
        print "New Orig : orig_x=%d orig_y=%d orig_z=%d " % self.orig

    def pos_to_index(self, pos, raw_pos = False):

        if not raw_pos:
           x = pos[0] - 8 - self.orig[0]
           y = pos[1] - 8 - self.orig[1]
           z = pos[2] - 8 - self.orig[2]
        else:
           x = pos[0]
           y = pos[1]
           z = pos[2]

        return int( x + y * self.length * self.width + z * self.width )

    def index_to_pos(self, index, raw_pos = False):

        x = index % self.width
        y = index / (self.length * self.width)
        z = (index / self.width ) % self.length

        if not raw_pos:
            x += 8 + self.orig[0]
            y += 8 + self.orig[1]
            z += 8 + self.orig[2]

        return ( x, y, z )

    def expand(self, size):

        new_ids = bytearray( len(self.ids) * size ** 3 )
        new_datas = bytearray( len(self.datas) * size ** 3 )

        index = len(self.ids) - 1
        while index >= 0:

            x, y, z = self.index_to_pos(index, True)

            new_index = size * ( x + size * ( z * self.width + size * y * self.length * self.width ) )

            new_ids[new_index] = self.ids[ index ]
            new_datas[new_index] = self.datas[ index ]

            index -= 1

        self.ids = new_ids
        self.datas = new_datas

        self.height *= size
        self.length *= size
        self.width *= size

        print "New Blocks : width=%d height=%d length=%d " % ( self.width, self.height, self.length )

