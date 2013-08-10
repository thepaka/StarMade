# -*- coding: utf-8 -*-
"""
@author: thepaka
@version: v0.1
"""

from itertools import chain

def db_x2( i, d ):

    plain = chain(
        # HULL
        range(42, 43+1), [ 98 ],
        # DOOR
        [ 64, 71, 96 ],
        # LIGHTS
        [ 89, 123, 124 ],
        # GLASSES
        [ 20, 102 ],
    )

    half_plain = chain(
        # Minecraft slabs
        [ 44, 126 ],
    )

    oriented = chain(
        # HULL WEDGES
        [ 53, 67, 108, 109, 114, 128, 134, 135, 136, 156 ],
    )

    if i in plain:

        ids = [ i, i, i, i,
                i, i, i, i, ]

        datas = [ d, d, d, d,
                  d, d, d, d, ]

    elif i in half_plain:

        up = ((d & 0x8) >> 3)

        if up:
            ids = [ 0, 0, i, i,
                    0, 0, i, i, ]

            datas = [ 0, 0, d, d,
                      0, 0, d, d, ]
        else:
            ids = [ i, i, 0, 0,
                    i, i, 0, 0, ]

            datas = [ d, d, 0, 0,
                      d, d, 0, 0, ]

    elif i in oriented:

        up = ((d & 0x4) >> 2)
        direction = d & 0x3

        pi = 42
        pd = 0

        if direction == 0x2:        # N
            if up:
                ids = [ 0, 0, i, i,
                        i, i, pi,pi, ]

                datas = [ 0, 0, d, d,
                          d, d, pd,pd, ]
            else:
                ids = [ i, i, 0, 0,
                       pi,pi, i, i, ]

                datas = [ d, d, 0, 0,
                         pd,pd, d, d, ]

        elif direction == up:       # W
            if up:
                ids = [ i, 0, pi, i,
                        i, 0, pi, i, ]

                datas = [ d, 0, pd, d,
                          d, 0, pd, d, ]
            else:
                ids = [ i,pi, 0, i,
                        i,pi, 0, i, ]

                datas = [ d,pd, 0, d,
                          d,pd, 0, d, ]

        elif direction == 0x3:      # S
            if up:
                ids = [ i, i, pi,pi,
                        0, 0, i, i, ]

                datas = [ d, d, pd,pd,
                          0, 0, d, d, ]
            else:
                ids = [pi,pi, i, i,
                        i, i, 0, 0, ]

                datas = [pd,pd, d, d,
                          d, d, 0, 0, ]

        elif direction == (not up): # E
            if up:
                ids = [ 0, i, i,pi,
                        0, i, i,pi, ]

                datas = [ 0, d, d,pd,
                          0, d, d,pd, ]
            else:
                ids = [pi, i, i, 0,
                       pi, i, i, 0, ]

                datas = [pd, d, d, 0,
                         pd, d, d, 0, ]

        else:
             raise ValueError

    else:

        ids = [ i, 0, 0, 0, 0, 0, 0, 0 ]
        datas = [ d, 0, 0, 0, 0, 0, 0, 0 ]

    return ids, datas


class Expander:

    def __init__(self):

        return

    def init(self, size, mc_id, mc_data):

        self.size = size

        if size == 2:
            self.ids , self.datas = db_x2( mc_id, mc_data )
        else:
            raise ValueError

    def get(self, pos):
        index = pos[0] + pos[2] * self.size ** 2 + pos[1] * self.size

        return self.ids[index], self.datas[index]

