package net.siebentupel.trainstore;


import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.material.Rails;


public final class MinecartAtJunctionListener implements Listener {
	private final Trainstore plugin;
	private Player player;
	
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
		
		// get the state of the block/rrail of the junction
		final BlockState junctionState = junctionBlock.getState();
		final Rails rail = (Rails) junctionState.getData();
		
		// get the cRailJunction object fot his block from the global junction list
		TrackJunction trackJunction = this.plugin.getJunction(junctionBlock);
		TrackStation trackStation = this.plugin.getStation(this.plugin.getPlayerDestination(player));
		
		Direction d = trackJunction.getDirection(trackStation);
		
		BlockFace face = event.getFromLocation().getFace(event.getToLocation());
		switch(face) {
		case NORTH:
			switch(d) {
			case EAST:
				rail.setDirection(BlockFace.NORTH_WEST, false);
				junctionState.update();
				break;
			case WEST:
				rail.setDirection(BlockFace.NORTH_EAST, false);
				junctionState.update();
				break;
			case SOUTH:
				rail.setDirection(BlockFace.NORTH, false);
				junctionState.update();
				break;
			case NORTH:
				rail.setDirection(BlockFace.NORTH, false);
				junctionState.update();
				break;
			}
			break;
		case EAST:
			switch(d) {
			case NORTH:
				rail.setDirection(BlockFace.SOUTH_EAST, false);
				junctionState.update();
				break;
			case SOUTH:
				rail.setDirection(BlockFace.NORTH_EAST, false);
				junctionState.update();
				break;
			case WEST:
				player.sendMessage("change direction to e-w");
				rail.setDirection(BlockFace.EAST, false);
				junctionState.update();
				break;
			case EAST:
				rail.setDirection(BlockFace.EAST, false);
				junctionState.update();
				break;
			}
			break;
		case SOUTH:
			switch(d) {
			case EAST:
				rail.setDirection(BlockFace.SOUTH_WEST, false);
				junctionState.update();
				break;
			case WEST:
				rail.setDirection(BlockFace.SOUTH_EAST, false);
				junctionState.update();
				break;
			case NORTH:
				rail.setDirection(BlockFace.SOUTH, false);
				junctionState.update();
				break;
			case SOUTH:
				rail.setDirection(BlockFace.SOUTH, false);
				junctionState.update();
				break;
			}
			break;
		case WEST:
			switch(d) {
			case NORTH:
				rail.setDirection(BlockFace.SOUTH_WEST, false);
				junctionState.update();
				break;
			case SOUTH:
				rail.setDirection(BlockFace.NORTH_WEST, false);
				junctionState.update();
				break;
			case EAST:
				rail.setDirection(BlockFace.WEST, false);
				junctionState.update();
				break;
			case WEST:
				player.sendMessage("change direction to w-w");
				rail.setDirection(BlockFace.WEST, false);
				junctionState.update();
				break;
			}
			break;
		}
		return;
    } 
}
