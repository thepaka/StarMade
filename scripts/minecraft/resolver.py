# -*- coding: utf-8 -*-
"""
@author: thepaka
@version: v0.1
"""

from itertools import chain

WEDGE  = 0
CORNER = 1

def minecraft_to_starmade( mc_id, mc_data ):

   sm_orientation = 0
   sm_hp = 0

   # Ship core
   if mc_id == 7:

      sm_id = 1
      sm_hp = 500

   # HULL
   elif mc_id == 42:

      sm_id = 5
      sm_hp = 100

   # DEFAULT
   else:

      sm_id = 5
      sm_hp = 100

   return ( sm_id, sm_orientation, sm_hp )

def starmade_orientation( sm_orientation, sm_type = WEDGE ):

   if sm_type == CORNER:

      if sm_orientation in range(0, 3+1):
         sm_orientation = 6
      elif sm_orientation in range(4, 7+1):
         sm_orientation = 2
      elif sm_orientation in range(8, 11+1):
         sm_orientation = 10
      elif sm_orientation in range(12, 15+1):
         sm_orientation = 14

   up = ((sm_orientation & 0x8) >> 3)
   if up:
      retval = 0x4
   else:
      retval = 0x0

   direction = sm_orientation & 0x7
   if direction == 0x0:      # N
      retval |= 0x2
   elif direction == 0x2:    # W
      retval |= up
   elif direction == 0x4:    # S
      retval |= 0x3
   elif direction == 0x6:    # E
      retval |= not up
   else:
      raise ValueError

   return retval


def starmade_to_minecraft( sm_id, sm_orientation ):

   mc_data = 0

   # Ship core
   if sm_id == 1:

       mc_id = 7

   # HULL
   elif sm_id in chain([ 5, 69, 70, 81 ], range(75, 79+1), range(263, 271+1)):

      mc_id = 42

   # HULL WEDGES
   elif sm_id in chain(range(293, 301+1), range(311, 319+1)):

      mc_id = 109

      try:
         mc_data = starmade_orientation( sm_orientation )
      except ValueError:
         mc_id = 42
         mc_data = 0

   # HULL CORNERS
   elif sm_id in chain(range(302, 310+1), range(320, 328+1)):

      mc_id = 109

      try:
         mc_data = starmade_orientation( sm_orientation , CORNER)
      except ValueError:
         mc_id = 42
         mc_data = 0

   # DOOR
   elif sm_id == 122:

      mc_id = 0

   # LIGHTS
   elif sm_id in chain([ 55, 62 ], range(282, 285+1)):

      #if not sm_orientation:
      #   mc_id = 123
      #else:
      #   mc_id = 124

      mc_id = 89

   # GLASSES
   elif sm_id in [ 63, 329, 330 ]:

      #mc_id = 102
      mc_id = 20

   # DEFAULT
   else:

      mc_id = 1

   return ( mc_id, mc_data )
