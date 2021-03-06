package net.siebentupel.trainstore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.ChatColor;

public final class ClickOnSignListener implements Listener {
	private final Trainstore plugin;
	
	public ClickOnSignListener(Trainstore plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void parseCommandsign(ClickOnSignEvent event) {
        if(Trainstore.isDestinationSign(event.getSign().getLine(0))) {
        	this.plugin.setPlayerDestination(event.getPlayer(), ChatColor.stripColor(event.getSign().getLine(1)));
			event.getPlayer().sendMessage("set destination to " + ChatColor.stripColor(event.getSign().getLine(1)));
        } else if(Trainstore.isJunctionSign(event.getSign().getLine(0))) {
        	
        } else if((Trainstore.isStationSign(event.getSign().getLine(0))) && event.getPlayer().hasPermission("router.update")) {
        	// get the next block that is a rail
        	if(Trainstore.isRail(event.getSign().getBlock().getRelative(1, 1, 0).getType())) {
    			Autorouter router = new Autorouter(this.plugin);
    			router.updateRoutes(event.getSign().getBlock().getRelative(1, 1, 0), event.getPlayer());
    		} 
        	else if(Trainstore.isRail(event.getSign().getBlock().getRelative(-1, 1, 0).getType())) {
    			Autorouter router = new Autorouter(this.plugin);
    			router.updateRoutes(event.getSign().getBlock().getRelative(-1, 1, 0), event.getPlayer());
    		} 
        	else if(Trainstore.isRail(event.getSign().getBlock().getRelative(0, 1, 1).getType())) {
    			Autorouter router = new Autorouter(this.plugin);
    			router.updateRoutes(event.getSign().getBlock().getRelative(0, 1, 1), event.getPlayer());
    		} 
        	else if(Trainstore.isRail(event.getSign().getBlock().getRelative(0, 1, -1).getType())) {
    			Autorouter router = new Autorouter(this.plugin);
    			router.updateRoutes(event.getSign().getBlock().getRelative(0, 1, -1), event.getPlayer());
    		}
        }
    }
}
