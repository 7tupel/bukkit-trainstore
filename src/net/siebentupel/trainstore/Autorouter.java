package net.siebentupel.trainstore;

import java.util.LinkedList;

import net.siebentupel.trainstore.exceptions.TrackException;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Autorouter {
	
	private LinkedList<Block> visited = new LinkedList<Block>();
	
	private LinkedList<TrackPoint> trackPoints = new LinkedList<TrackPoint>();

	private Trainstore plugin;
	
	public Autorouter(Trainstore plugin) {
		this.plugin = plugin;
	}
	
	public void updateRoutes(Block root, Player player) throws TrackException {
		// clear old routing table and station list
		this.plugin.clearStations();
		this.plugin.clearRoutingTable();
		// first check if the block is a station block (=end of track)
		if(!isStation(root)) {
			player.sendMessage("root is not a station");
			throw new TrackException("routing update must start at a station!");
		}
		// make a new station
		TrackStation station = new TrackStation(root);
		// add the station to the global station list
		this.plugin.addStation(station);
		player.sendMessage("found first station");
		// make a new line with this station as start
		RailLine line = new RailLine();
		// add line to the global line list
		this.plugin.addRailLine(line);
		// set station as one end of the trackline
		line.setStart(station);
		// add the line as connected to the station
		station.addLine(line);
		// add the current block as a block of the trackline
		line.addBlock(root);
		// set the block as visited
		visited.add(root);
		// call update for recursive route search
		updateRoutes(root, line, player);
	}
	
	public void updateRoutes(Block block, RailLine line, Player player) {
		//player.sendMessage("recursive call");
		LinkedList<Block> next = getNextTracks(block);
		// go through all rails next to the current
		for(int i=0;i<next.size();i++) {
			//player.sendMessage("looping through neighbors");
			Block currentBlock = next.get(i);
			// first check if the rail was not yet visited
			if(!(visited.contains(currentBlock))) {
				// distinguish between the three types of TrackPoints: simple Rail, Station and Router/Junction
				// it is a station
				if(isStation(currentBlock)) {
					player.sendMessage("found new station");
					// add the block to the line
					line.addBlock(currentBlock);
					// make a new station and add it to the track
					TrackStation end = new TrackStation(currentBlock);
					line.setEnd(end);
					// add the station to the global station list
					this.plugin.addStation(end);
					// add the current line to the station
					end.addLine(line);
				}
				// it is a junction/router
				else if(isJunction(currentBlock)) {
					player.sendMessage("found new junction");
					// create a new junction
					TrackRouter junction = new TrackRouter(currentBlock);
					// add the junction to the global junction map
					this.plugin.addJunction(junction);
					// add the block to the current line
					line.addBlock(currentBlock);
					// add the junction block as end of the line
					line.setEnd(junction);
					// add the line to the junction
					junction.addLine(line);
					// mark the current block as visited
					visited.add(currentBlock);
					// now check neighbor blocks for rails. there should be at least 3 and max 4
					LinkedList<Block> junctionNeighbors = getNextTracks(block);
					// go trough all neighbors
					for(Block item : junctionNeighbors) {
						// if the neighbor was not yet visited
						if(!(visited.contains(item))) {
							// start a new line
							RailLine newLine = new RailLine();
							// add line to the global line list
							this.plugin.addRailLine(newLine);
							// add the block to the line
							newLine.addBlock(item);
							// set the junction as start for the new line
							newLine.setStart(junction);
							// add the line to the junction
							junction.addLine(newLine);
							// recursive call
							updateRoutes(item, newLine, player);
						}
					}
				} 
				// just a simple rail
				else {
					player.sendMessage("found new rail");
					// add the current block to the line
					line.addBlock(currentBlock);
					// mark current block as visited
					visited.add(currentBlock);
					// continue seach
					updateRoutes(currentBlock, line, player);
				}
			}
		}
	}
	
	
	private boolean isJunction(Block block) {
		int counter = 0;
		if(Trainstore.isRail(block.getRelative(1, 0, 0).getType())) {
			counter++;
		}
		if(Trainstore.isRail(block.getRelative(-1, 0, 0).getType())) {
			counter++;
		}
		if(Trainstore.isRail(block.getRelative(0, 0, 1).getType())) {
			counter++;
		}
		if(Trainstore.isRail(block.getRelative(0, 0, -1).getType())) {
			counter++;
		}
		if(counter >=3)
			return true;
		else
			return false;
	}
	
	private boolean isStation(Block block) {
		int counter = 0;
		if(Trainstore.isRail(block.getRelative(1, 0, 0).getType())) {
			counter++;
		}
		if(Trainstore.isRail(block.getRelative(-1, 0, 0).getType())) {
			counter++;
		}
		if(Trainstore.isRail(block.getRelative(0, 0, 1).getType())) {
			counter++;
		}
		if(Trainstore.isRail(block.getRelative(0, 0, -1).getType())) {
			counter++;
		}
		if(counter == 1)
			return true;
		else
			return false;
	}
	
	private LinkedList<Block> getNextTracks(Block block) {
		LinkedList<Block> list = new LinkedList<Block>();
		for(int x=-1; x<2; x++) {
			for(int y=-1; y<2; y++) {
				for(int z=-1; z<2; z++) {
					Block neighbor = block.getRelative(x, y, z);
					if(Trainstore.isRail(neighbor.getType())) {
						list.add(neighbor);
					}
				}
			}
		}
		return list;
	}
}
