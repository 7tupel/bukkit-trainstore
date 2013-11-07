package net.siebentupel.trainstore;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.material.Rails;


public final class MinecartAtJunctionListener implements Listener {
	private final Trainstore plugin;
	private Player player;
	private LinkedList<String[]> routingTable = new LinkedList<String[]>();
	
	public MinecartAtJunctionListener(Trainstore plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void routeMinecart(MinecartAtJunctionEvent event) {
		if(event.getCart().getPassenger() instanceof Player) {
			player = ((Player)event.getCart().getPassenger());
		} else {
			return;
		}
		
		
		// make the routing table
		Block junctionBlock = event.getToLocation();
		Block block;
		BlockState state;
		
		// go trough all direct neighbor blocks up to 4 levels down
		for(int y=1; y<5; y++) {
			for(int x=-1; x<2; x++) {
				for(int z=-1; z<2; z++) {
					// get the block
					block = junctionBlock.getRelative(x, -y, z);
					//player.sendMessage("searching block ("+ block.getX()+", "+block.getY() + ", " + block.getZ() + ")");
					state = block.getState();
					// check if there is a sign
					if(state instanceof Sign) {
						Sign sign = (Sign)state;
						// check if the sign is a valid junction sign
						if(Trainstore.isJunctionSign(sign.getLine(0))) {
							//player.sendMessage("found junction sign");
							// get all routing entries of the junction sign
							for(int m=1; m<4; m++) {
								try {
									// split line
									String line = ChatColor.stripColor(sign.getLine(m));
									String[] tuple = line.split(":");
									// if the routing entry is valid (just checking if array has 2 entries), save it
									if(tuple.length == 2) {
										routingTable.add(tuple);
									}
								} catch (Exception e) {
									// just ignore it
								}
							}
						}
					}
				}
			}
		}
		
		//player.sendMessage("player destination length: "+plugin.getPlayerDestination(player).length());
		
		// now go through the routing table and check if there is an entry with the player's current location
		for(int i=0; i<routingTable.size(); i++) {
			//player.sendMessage("destination:= "+routingTable.get(i)[0] + "; direction:= "+ routingTable.get(i)[1] + "; length:= "+routingTable.get(i)[0].length());
			if(routingTable.get(i)[0].equalsIgnoreCase(plugin.getPlayerDestination(player))) {
				//player.sendMessage("found routing entry");
				//final MaterialData d = junctionBlock.getState().getData();
				final BlockState junctionState = junctionBlock.getState();
				final Rails rail = (Rails) junctionState.getData();
				BlockFace face = event.getFromLocation().getFace(event.getToLocation());
				switch(face) {
				case NORTH:
					switch(routingTable.get(i)[1]) {
					case "e":
						rail.setDirection(BlockFace.NORTH_WEST, false);
						junctionState.update();
						break;
					case "w":
						rail.setDirection(BlockFace.NORTH_EAST, false);
						junctionState.update();
						break;
					case "s":
						rail.setDirection(BlockFace.NORTH, false);
						junctionState.update();
						break;
					case "n":
						rail.setDirection(BlockFace.NORTH, false);
						junctionState.update();
						break;
					}
					break;
				case EAST:
					switch(routingTable.get(i)[1]) {
					case "n":
						rail.setDirection(BlockFace.SOUTH_EAST, false);
						junctionState.update();
						break;
					case "s":
						rail.setDirection(BlockFace.NORTH_EAST, false);
						junctionState.update();
						break;
					case "w":
						player.sendMessage("change direction to e-w");
						rail.setDirection(BlockFace.EAST, false);
						junctionState.update();
						break;
					case "e":
						rail.setDirection(BlockFace.EAST, false);
						junctionState.update();
						break;
					}
					break;
				case SOUTH:
					switch(routingTable.get(i)[1]) {
					case "e":
						rail.setDirection(BlockFace.SOUTH_WEST, false);
						junctionState.update();
						break;
					case "w":
						rail.setDirection(BlockFace.SOUTH_EAST, false);
						junctionState.update();
						break;
					case "n":
						rail.setDirection(BlockFace.SOUTH, false);
						junctionState.update();
						break;
					case "s":
						rail.setDirection(BlockFace.SOUTH, false);
						junctionState.update();
						break;
					}
					break;
				case WEST:
					switch(routingTable.get(i)[1]) {
					case "n":
						rail.setDirection(BlockFace.SOUTH_WEST, false);
						junctionState.update();
						break;
					case "s":
						rail.setDirection(BlockFace.NORTH_WEST, false);
						junctionState.update();
						break;
					case "e":
						rail.setDirection(BlockFace.WEST, false);
						junctionState.update();
						break;
					case "w":
						player.sendMessage("change direction to w-w");
						rail.setDirection(BlockFace.WEST, false);
						junctionState.update();
						break;
					}
					break;
				}
			}
		}
		routingTable.clear();
		return;
    }
}
