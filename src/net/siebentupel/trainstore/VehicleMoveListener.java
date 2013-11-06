package net.siebentupel.trainstore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public final class VehicleMoveListener implements Listener {
	
	@EventHandler
    public void onMove(VehicleMoveEvent event) {
		// check if the vehicle is a minecart
		if(event.getVehicle().getType() == EntityType.MINECART) {
			// check if the minecart is on an rail
			Location loc = event.getVehicle().getLocation();
			// therefore check if the block under the current location is a rail
			if(loc.subtract(0, -1, 0).getBlock().getType() == Material.RAILS){
				Bukkit.getServer().getPluginManager().callEvent(new MinecartAtJunctionEvent(event.getVehicle()));
			}
		}
	}
}
