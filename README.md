# Trainstore #

Trainstore is a simple but yet powerfull automatic Minecart routing plugin for Bukkit.

The current version 0.2 is the first release with autorouting functionality. But there are still some limitations (mainly there can only be one railnetwork per worl that is autorouted if you create two or more different unconnected railnetworks only one will be autorouted).

I used the existing Bukkit plugin [PdxTrackRouter](https://github.com/glguy/PdxTrackRouter) as an source of inspiration, but i did not use its code. But still, Trainstore stays under the GPLv3.

## Changelog ##

### v0.2 ###

* Autorouter - junction signs are no longer needed

## How to use ##

Trainstore automatic routing works with **destinations** and **stations**.
A destination is either set via the *ts-set-destination DESTINATION* command or destination signs. A destination sign looks like

1. [tsdestination]
2. DESTINATIONIDENTIFIER
3. additional name or description (optinal)

If a player does not set a destination *default* is used.

To route a Minecart just create a 3 or 4 way junction as usual. Nothing further to do.

For the autorouter to work as intended you need to add stations to your rail network. This is done by **tsstation** signs like

1. [tsstation]
2. DESTINATIONIDENTIFIER

The identifier is the same as on the destination signs. The sign has to be placed on one of the 4 side faces of the block with the endrail of a track. This is then the station.

To start the autorouter you need to have the permissions (see below) and then right click on one stationsign. You will see some information about the routing process in your message window.


All commands available at the moment are
*   /ts-set-destination DESTINATION - set the players destination to DESTINATION
*   /ts-clear-destination - clear the players destination (setting it to default)
*   /ts-destination - get the current destination of the player
*   /ts-list-stations - get a list of all stations available
*   /ts-list-junctions - get a list of all junctions of the railnetwork


Permissions:
*   destination.set - set a destination for oneself 
*   destination.clear - clear destination for oneself
*   destination.get - description: get current destination for oneself
*   station.list - description: list all stations
*   junction.list - description: list all junctions (op only)
*   router.update - description: update all routes (op only)


