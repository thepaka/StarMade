# -*- coding: utf-8 -*-
"""
@author: thepaka
@version: v0.1
"""

import db

from itertools import chain

WEDGE  = 0
CORNER = 1

def minecraft_orientation( mc_data ):

   up = ((mc_data & 0x4) >> 2)
   if up:
      retval = 0x8
   else:
      retval = 0x0

   direction = mc_data & 0x3
   if direction == 0x2:        # N
      retval |= 0x0
   elif direction == up:       # W
      retval |= 0x2
   elif direction == 0x3:      # S
      retval |= 0x4
   elif direction == (not up): # E
      retval |= 0x6
   else:
      raise ValueError

   return retval

def minecraft_to_starmade( mc_id, mc_data ):

   sm_orientation = 0
   sm_active = 0

   # Ship core
   if mc_id == 7:

      sm_id = 1

   # HULL
   elif mc_id in chain(range(42, 43+1) ):

      sm_id = 5

   elif mc_id in [ 44, 98 ]:

      sm_id = 75

   # HULL WEDGES
   elif mc_id in [ 53, 67, 108, 109, 114, 128, 134, 135, 136, 156 ]:

      sm_id = 296

      try:
         sm_orientation = minecraft_orientation( mc_data )
      except ValueError:
         sm_id = 5

   # DOOR
   elif mc_id in [ 64, 71, 96 ]:

      sm_id = 122
      #sm_active = 1

   # LIGHTS
   elif mc_id in [ 89, 123, 124 ]:

      sm_id = 55

      if mc_id == 123:
         sm_active = 1

   # GLASSES
   elif mc_id in [ 20, 102 ]:

      sm_id = 63

   # DEFAULT
   else:

      sm_id = 0


   # Set Hitpoints
   if sm_id in db.hitpoints:
      sm_hp = db.hitpoints[sm_id]
   elif sm_id == 0:
      sm_hp = 0
   else:
      sm_hp = 100

   return ( sm_id, sm_hp, sm_orientation, sm_active )

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

      mc_id = 89

   # GLASSES
   elif sm_id in [ 63, 329, 330 ]:

      #mc_id = 102
      mc_id = 20

   # DEFAULT
   else:

      mc_id = 1

   return ( mc_id, mc_data )
