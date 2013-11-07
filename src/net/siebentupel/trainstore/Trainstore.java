package net.siebentupel.trainstore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;



/**
 * 
 * @author Moritz '7tupel' Moxter
 * @since  2013-11-06
 *
 * the Trainstore plugin
 */
public final class Trainstore extends JavaPlugin {
	/** the header of destination signs */
	private static final String DESTINATION_HEADER = "[tsdestination]";
	
	/** the header of junction signs */
	private static final String JUNCTION_HEADER = "[tsjunction]";
	
	private static final String STATION_HEADER = "[tsstation]";
	
	/** a map to store the destination of a player */
	private Map<Player, String> playerDestination = new HashMap<Player, String>();
	
	/** */
	private LinkedList<TrackStation> stations = new LinkedList<TrackStation>();
	
	/** */
	private LinkedList<RailLine> lines = new LinkedList<RailLine>();
	
	/** <TrackRouter(Junctions) <TrackStation(Destination), Direction>> */
	private HashMap<TrackJunction, HashMap<TrackStation, Direction>> routingTable = new HashMap<TrackJunction, HashMap<TrackStation, Direction>>();
	
	/**
	 * create all necessary data structures for the plugin and register eventhandlers
	 */
	@Override
    public void onEnable(){
        /* add some event listeners */
		// register custom Listener for PlayerLogin Event
		getServer().getPluginManager().registerEvents(new PlayerLoginListener(this), this);
		// register custom Listener for the PlayerInteractEntity Event
		getServer().getPluginManager().registerEvents(new PlayerRightclickListener(this), this);
		// register a custom listener for VehicleMove Events
		getServer().getPluginManager().registerEvents(new VehicleMoveListener(), this);
		// register the Listener for the "click on sign" event. called when a user right clicks on a sign
		getServer().getPluginManager().registerEvents(new ClickOnSignListener(this), this);
		// register the Listener for the "minecart at junction" event. called when a minecard gets on a junction
		getServer().getPluginManager().registerEvents(new MinecartAtJunctionListener(this), this);
		
		
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
    
    
    /**
     * set a destination for a player
     * @param player	the player we set a destination for
     * @param destination	the destination of the player
     */
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
    
    /**
     * get the current destination of a player
     * @param player	the player we would like to get the destination from
     * @return	the destination of the player
     */
    public String getPlayerDestination(Player player) {
    	if(this.playerDestination.containsKey(player)) {
    		return this.playerDestination.get(player);
    	} else {
    		// this is a dirty workaround for not throwing an Exception
    		return "default";
    	}
    }
    
    /**
     * log a message. may be called from listeners
     * @param message	the message we would like to logg
     */
    public void logMessage(String message) {
    	getLogger().info(message);
    }
    
    /**
     * dispatch all commands
     */
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	// set destination command
    	if (cmd.getName().equalsIgnoreCase("ts-set-destination")){ 
    		if ((sender instanceof Player) && (args.length == 1)) {
    			this.setPlayerDestination((Player) sender, args[0]);
    			sender.sendMessage("set player destination to "+args[0]);
    		} else if(args.length != 1) {
    			sender.sendMessage("incorrect number of parameters. takes exactly one");
    		} else {
    			return false;
    		}
    	} 
    	// clear a players destination
    	else if (cmd.getName().equalsIgnoreCase("ts-clear-destination")) {
    		if (sender instanceof Player) {
    			this.setPlayerDestination((Player) sender, "default");
    			sender.sendMessage("cleared player destination. set to default");
    		} else {
    			return false;
    		}
    		return true;
    	} 
    	// get the current destination of the player
    	else if (cmd.getName().equalsIgnoreCase("ts-destination")) {
    		if (sender instanceof Player) {
    			sender.sendMessage("current destination: " + this.getPlayerDestination((Player)sender));
    		} else {
    			return false;
    		}
    		return true;
    	}
    	// list all stations avaiable
    	else if (cmd.getName().equalsIgnoreCase("ts-list-stations")) {
    		if (sender instanceof Player) {
    			sender.sendMessage("available stations:");
    			for(int i=0; i<this.stations.size(); i++) {
    				sender.sendMessage("station with name " + stations.get(i).getName()+
    						" at (" + stations.get(i).getBlock().getX()+
    						", "+stations.get(i).getBlock().getY()+
    						" ,"+stations.get(i).getBlock().getZ()+")");
    			}
    		} else {
    			return false;
    		}
    		return true;
    	}
    	// list all junctions available
    	else if (cmd.getName().equalsIgnoreCase("ts-list-junctions")) {
    		if (sender instanceof Player) {
    			sender.sendMessage("available junctions:");
    			Set<TrackJunction> junctions = routingTable.keySet();
    			for(TrackJunction item : junctions) {
    				sender.sendMessage("junction at "+
    						"("+item.getBlock().getX()+" ,"+
    						"("+item.getBlock().getY()+" ,"+
    						"("+item.getBlock().getZ()+")"+
    						" with cardinality "+item.getCardinality());
    			}
    		} else {
    			return false;
    		}
    		return true;
    	}
    	// list all lines available
    	else if (cmd.getName().equalsIgnoreCase("ts-list-lines")) {
    		if (sender instanceof Player) {
    			sender.sendMessage("available lines:");
    			for(RailLine item : this.lines) {
    				sender.sendMessage("line starting at ("+item.getStart().getBlock().getX()+ ", "+
    						item.getStart().getBlock().getY()+ ", "+
    						item.getStart().getBlock().getZ()+ "); ending at ("+
    						item.getEnd().getBlock().getX()+ ", "+
    						item.getEnd().getBlock().getY()+ ", "+
    						item.getEnd().getBlock().getZ()+ ") with "+ item.getWeight() + " blocks");
    			}
    		} else {
    			return false;
    		}
    		return true;
    	}
    	// list junction neighbors
    	else if (cmd.getName().equalsIgnoreCase("ts-list-lines")) {
    		if (sender instanceof Player) {
    			sender.sendMessage("available lines:");
    			Set<TrackJunction> junctions = routingTable.keySet();
    			for(TrackJunction item : junctions) {
    				sender.sendMessage("junction at "+
    						"("+item.getBlock().getX()+" ,"+
    						""+item.getBlock().getY()+" ,"+
    						""+item.getBlock().getZ()+")"+
    						" with cardinality "+item.getCardinality() + " and neighbors:");
    				//for(TrackPoint point : item.getConnectedLines())
    			}
    		} else {
    			return false;
    		}
    		return true;
    	}
    	return false;
    	
    }

    /**
     * check if the given line of a sign matches the DESTINATION Sign header
     * @param line 		the line we would like to match
     * @return			true iff success, false else
     */
    public static boolean isDestinationSign(String line) {
    	return DESTINATION_HEADER.equalsIgnoreCase(ChatColor.stripColor(line));
    }
    
    /**
     * check if the given line of a sign matches the JUNCTION Sign header
     * @param line		the line we would like to match
     * @return			true iff sucess, false else
     */
    public static boolean isJunctionSign(String line) {
    	return JUNCTION_HEADER.equalsIgnoreCase(ChatColor.stripColor(line));
    }
    
    public static boolean isStationSign(String line) {
    	return STATION_HEADER.equalsIgnoreCase(ChatColor.stripColor(line));
    }
    
    public static boolean isRail(Material material) {
    	if((material == Material.RAILS) || (material == Material.POWERED_RAIL)
    			|| (material == Material.ACTIVATOR_RAIL) || (material == Material.DETECTOR_RAIL)) {
    		return true;
    	}
    	return false;
    }
    
    public void addStation(TrackStation station) {
    	this.stations.add(station);
    }
    
    public LinkedList<TrackStation> getAllStations() {
    	return this.stations;
    }
    
    public HashMap<TrackJunction, HashMap<TrackStation, Direction>> getRoutingTable() {
    	return this.routingTable;
    }
    
    public Direction getDirection(TrackJunction junction, TrackStation destination) {
    	return this.routingTable.get(junction).get(destination);
    }
    
    public void addJunction(TrackJunction junction) {
    	this.routingTable.put(junction, new HashMap<TrackStation, Direction>());
    }
    
    public void addRailLine(RailLine line) {
    	this.lines.add(line);
    }
    
    public LinkedList<RailLine> getLines() {
    	return this.lines;
    }
    
    public void clearStations() {
    	this.stations.clear();
    }
    
    public void clearRoutingTable() {
    	this.routingTable.clear();
    }
    
    public void clearLines() {
    	this.lines.clear();
    }
    
}