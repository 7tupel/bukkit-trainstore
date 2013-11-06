package net.siebentupel.trainstore;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.material.Rails;


public final class MinecartAtJunctionListener implements Listener {
	private Trainstore plugin;
	public MinecartAtJunctionListener(Trainstore plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void routeJunction(MinecartAtJunctionEvent event) {
        // Some code here
		// now the minecart is on a rail an moving
		/* check for routing signs under the current block */
		Location location = event.getCart().getLocation();
		Location searchLocation;
		LinkedList<String[]> destinationTuples = new LinkedList<String[]>();
		String line;
		String[] destination;
		// go through all fields north, south, east, west, and the field self...
		for(int k=-1; k<2;k++) {
			for(int l=-1; l<2;l++) {
					// ...and go down up to 4 blocks
					for(int m=0; m<4;m++) {
						// create a copy of the location to work with
						searchLocation = location.clone();
						// modify location to match the blocks north, south, east, west, down to the vehicle
						searchLocation.add(k, -m, l);
						// check if the block is a sign...
						if((searchLocation.getBlock().getType() == Material.SIGN) || (searchLocation.getBlock().getType() == Material.SIGN_POST)) {
							// ...and if the sign is a junction sign
							if(((Sign)searchLocation.getBlock()).getLine(0) == "[tsjunction]") {
								// go through lines 2 to 4
								for(int j=1; j<4; j++) {
									try {
										line = ((Sign)searchLocation.getBlock()).getLine(j);
										// separate destination and direction
										destination = line.split(":");
										// add destination,direction tuple to the list
										destinationTuples.add(destination);
									} catch (Exception e) {
										// just ignore it
									} 
								}
							}
						}
					}
			}
		}
		/* now we have a list of all destinations and their routing information available at this juntion */
		
		// check if the player's destination is in the routing list
		for(int i=0; i<destinationTuples.size(); i++) {
			try {
				// found destination
				if(destinationTuples.get(i)[0] == this.plugin.getPlayerDestination((Player)event.getCart().getPassenger())) {
					// set the direction of the rail accordingly to the routing information
					searchLocation = location.clone();
					Rails rail = (Rails) searchLocation.subtract(0, 1, 0).getBlock();
					switch(destinationTuples.get(i)[1]) {
					case "n":
						// first check if the block next in this direction is a rail
						if(searchLocation.add(0,0,-1).getBlock().getType() == Material.RAILS) {
							this.plugin.logMessage("set rail direction north");
							rail.setDirection(BlockFace.NORTH, false);
						}
						break;
					case "s":
						// first check if the block next in this direction is a rail
						if(searchLocation.add(0,0,1).getBlock().getType() == Material.RAILS) {
							this.plugin.logMessage("set rail direction south");
							rail.setDirection(BlockFace.SOUTH, false);
						}
						break;
					case "e":
						// first check if the block next in this direction is a rail
						if(searchLocation.add(1,0,0).getBlock().getType() == Material.RAILS) {
							this.plugin.logMessage("set rail direction east");
							rail.setDirection(BlockFace.EAST, false);
						}
						break;
					case "w":
						// first check if the block next in this direction is a rail
						if(searchLocation.add(-1,0,0).getBlock().getType() == Material.RAILS) {
							this.plugin.logMessage("set rail direction west");
							rail.setDirection(BlockFace.WEST, false);
						}
						break;
					}
				}
			} catch (Exception e) {
				
			}
		}
		
		/*
		int i = 0;
		while(i<4) {
			// block west
			searchLocation = location.clone();
			searchLocation.add(1, -i, 0);
			// block is a sign
			if((searchLocation.getBlock().getType() == Material.SIGN) || (searchLocation.getBlock().getType() == Material.SIGN_POST)) {
				// and the sign is a junction sign
				if(((Sign)searchLocation.getBlock()).getLine(0) == "[tsjunction]") {
					// go through lines 2 to 4
					for(int j=1; j<4; j++) {
						try {
							line = ((Sign)searchLocation.getBlock()).getLine(j);
							// separate destination and direction
							destination = line.split(":");
							// add destination,direction tuple to the list
							destinationTuples.add(destination);
						} catch (Exception e) {
							// just ignore it
						} 
					}
				}
			}
			// block south
			searchLocation = location.clone();
			searchLocation.add(0, -i, 1);
			// block east
			searchLocation = location.clone();
			searchLocation.add(-1, -i, 0);
			// block north
			searchLocation = location.clone();
			searchLocation.add(0, -i, -1);
			// increase loop variable
			i++;
		} */
    }
}
