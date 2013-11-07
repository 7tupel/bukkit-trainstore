# Trainstore #

Trainstore is a simple but yet powerfull automatic Minecart routing plugin for Bukkit.

The current version 0.1 is the first release. At this time there are just few features.

I used the existing Bukkit plugin [PdxTrackRouter](https://github.com/glguy/PdxTrackRouter) as an source of inspiration, but i did not use its code. But still, Trainstore stays under the GPLv3.

## How to use ##

Trainstore automatic routing works with **destinations** and **junctions**.
A destination is either set via the *ts-set-destination DESTINATION* command or destination signs. A destination sign looks like

1. [tsdestination]
2. DESTINATIONIDENTIFIER
3. additional name or description (optinal)

To route a Minecart just create a 3 or 4 way junction as usual. Then place a junction sign either in one of the corners of the junction on the level of the block under the junction. You can place signs up to 4 blocks down of the corners as well. It is possible to place as many junction signs as fit, that are 4 signs a level at 4 levels making up to 48 routing options per junction. The signs are as follows:

1. [tsdestination]
2. DESTINATION:DIRECTION
3. DESTINATION:DIRECTION
4. ...
where a destination is a string with the name of the destination an direction is one of characters **n**, **s**, **e** and **w** each for the directions north, south, east and west.

If a player does not set a destination *default* is used.

All commands available at the moment are
*   /ts-set-destination DESTINATION - set the players destination to DESTINATION
*   /ts-clear-destination - clear the players destination (setting it to default)
*   /ts-destination - get the current destination of the player

