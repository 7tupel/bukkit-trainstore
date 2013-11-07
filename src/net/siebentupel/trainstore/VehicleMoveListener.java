package net.siebentupel.trainstore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public final class VehicleMoveListener implements Listener {
	
	@EventHandler
    public void onMove(VehicleMoveEvent event) {
		
		final Location fromLocation = event.getFrom();
        final Location toLocation = event.getTo();
        		
        // if we are still on the same block, no need to do anything here
        if (fromLocation.getBlockX() == toLocation.getBlockX()
                && fromLocation.getBlockY() == toLocation.getBlockY()
                && fromLocation.getBlockZ() == toLocation.getBlockZ()) {
        	return;
        }
        
		// check if the vehicle is a minecart
		if( (event.getVehicle() instanceof Minecart)) {
			// check if the minecart is on an rail
			Location loc = event.getVehicle().getLocation();
			// check if there is a rail on the block
			Block block = loc.getBlock();
			if(block.getType() == Material.RAILS){
				// predict the next block the user travels to
				//Direction direction = MovementPredictor.getDirection(fromLocation, toLocation);
				// check if the block we go to has rails too
				if(toLocation.getBlock().getType() == Material.RAILS) {
					if(MovementPredictor.isJunction(toLocation.getBlock())) {
						Bukkit.getServer().getPluginManager().callEvent(new MinecartAtJunctionEvent(event.getVehicle(), fromLocation.getBlock(), toLocation.getBlock()));
					}
				}
			}
		} else {
			return;
		}
	}
}
