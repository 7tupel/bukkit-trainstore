package net.siebentupel.trainstore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.ChatColor;
import org.bukkit.Material;

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
        	
        } else if(Trainstore.isStationSign(event.getSign().getLine(0))) {
        	// get the next block that is a rail
        	if(event.getSign().getBlock().getRelative(1, 0, 0).getType() == Material.RAILS) {
    			Autorouter router = new Autorouter(this.plugin);
    			router.updateRoutes(event.getSign().getBlock().getRelative(1, 0, 0));
    		} else if(event.getSign().getBlock().getRelative(-1, 0, 0).getType() == Material.RAILS) {
    			Autorouter router = new Autorouter(this.plugin);
    			router.updateRoutes(event.getSign().getBlock().getRelative(-1, 0, 0));
    		} else if(event.getSign().getBlock().getRelative(0, 0, 1).getType() == Material.RAILS) {
    			Autorouter router = new Autorouter(this.plugin);
    			router.updateRoutes(event.getSign().getBlock().getRelative(0, 0, 1));
    		} else if(event.getSign().getBlock().getRelative(0, 0, -1).getType() == Material.RAILS) {
    			Autorouter router = new Autorouter(this.plugin);
    			router.updateRoutes(event.getSign().getBlock().getRelative(0, 0, -1));
    		}
        }
    }
}
