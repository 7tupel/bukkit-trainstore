package net.siebentupel.trainstore;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;



public final class Trainstore extends JavaPlugin {
	/**
	 * a map to store the destination of a player
	 */
	private Map<Player, String> playerDestination = new HashMap<Player, String>();
	
	@Override
    public void onEnable(){
        /* add some event listeners */
		// register custom Listener for the PlayerInteractEntity Event
		// necessary to call a ClickOnSignEvent
		getServer().getPluginManager().registerEvents(new PlayerRightclickListener(this), this);
		// register custom Listener for 
		// necessary to call a MinecartAtJunctionEvent if and when a minecart is at a junction
		// ...
		getServer().getPluginManager().registerEvents(new VehicleMoveListener(), this);
		// register the Listener for the "click on sign" event
		//getServer().getPluginManager().registerEvents(new ClickOnSignListener(this), this);
		// register the Listener for the "minecart at junction" event
		getServer().getPluginManager().registerEvents(new MinecartAtJunctionListener(), this);
		
		// set a default location for all players online
	    for (Player player : this.getServer().getOnlinePlayers()) {
	    	this.playerDestination.put(player, "default");
	    }
		
		getLogger().info("plugin \"Trainstore\" enabled");
    }
 
    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    	getLogger().info("plugin \"Trainstore\" disabled");
    }
    
    
    public void setPlayerDestination(Player player, String destination) {
    	// if there is an destination entry for this player
    	if(this.playerDestination.containsKey(player) == true) {
    		// remove the entry
    		this.playerDestination.remove(player);
    		// set new destination
    		this.playerDestination.put(player, destination);
    	}
    	// there is no destination set for this player
    	// should never occur, because we set a default destination for each player
    	else {
    		this.playerDestination.put(player, destination);
    	}
    }
    
    public String getPlayerDestination(Player player) {
    	if(this.playerDestination.containsKey(player)) {
    		return this.playerDestination.get(player);
    	} else {
    		// this is a dirty workaround for not throwing an Exception
    		return "default";
    	}
    }
    
    
}