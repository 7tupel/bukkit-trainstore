package net.siebentupel.trainstore;

import java.util.LinkedList;

import net.siebentupel.trainstore.exceptions.TrackException;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Autorouter {
	
	private LinkedList<Block> visited = new LinkedList<Block>();
	
	private Trainstore plugin;
	
	public Autorouter(Trainstore plugin) {
		this.plugin = plugin;
	}
	
	public void updateRoutes(Block root, Player player) throws TrackException {
		// clear old routing table and station list
		this.plugin.clearStations();
		this.plugin.clearRoutingTable();
		this.plugin.clearLines();
		// first check if the block is a station block (=end of track)
		if(!isStation(root)) {
			throw new TrackException("routing update must start at a station!");
		}
		// make a new station
		TrackStation station = new TrackStation(root);
		// add the station to the global station list
		this.plugin.addStation(station);
		// make a new line with this station as start
		RailLine line = new RailLine();
		// add line to the global line list
		this.plugin.addRailLine(line);
		// set station as one end of the trackline
		line.setStart(station);
		// add the line as connected to the station
		station.addLine(line);
		// add the current block as a block of the trackline
		// do not, just add blocks to the line that are not start or end
		//line.addBlock(root);
		// set the block as visited
		visited.add(root);
		// get the next rail
		LinkedList<Block> next = getNextTracks(root);
		if(next.size() != 1)
			throw new TrackException("could not find a rail leaving the station");
		// call update for recursive route search
		updateRoutes(next.getFirst(), line, player);
		
		// now set routing tables for all junctions
		for(TrackJunction junction : this.plugin.getRoutingTable().keySet()) {
			junction.updateRoutingTable();
		}
		int k = this.plugin.getRoutingTable().size();
		for(int i=0; i<k; i++) {
			for(TrackJunction junction : this.plugin.getRoutingTable().keySet()) {
				junction.updateRoutingTable2(player);
			}
		}
	}
	
	public void updateRoutes(Block currentBlock, RailLine line, Player player) {
		// switch between the three possibilities: station, junction and simple rail
		if(isStation(currentBlock)) {
			// block is a station
			player.sendMessage("found new station");
			// make a new station and add it to the track
			TrackStation station = new TrackStation(currentBlock);
			// set the new station as end of the line
			line.setEnd(station);
			// add the line to the station's connected lines
			station.addLine(line);
			// add the station to the global station list
			this.plugin.addStation(station);
			// to make sure not to visit a rail twice mark the block as visited
			visited.add(currentBlock);
			// no recursive calls
		} else if(isJunction(currentBlock)) {
			// block is a junction
			player.sendMessage("found new junction");
			// create a new junction
			TrackJunction junction = new TrackJunction(currentBlock);
			// add the junction to the global junction list
			this.plugin.addJunction(junction);
			// set the junction as end point of the current line
			line.setEnd(junction);
			// inform the junction about the line it is connected to
			junction.addLine(line);
			// mark the block of the junction as visited
			visited.add(currentBlock);
			// get all rail blocks connected to this block
			LinkedList<Block> neighbors = getNextTracks(currentBlock);
			// now for all neighbor rails blocks
			for(Block neighbor : neighbors) {
				// check if the block was visited
				if(!visited.contains(neighbor)) {
					// block was not visited yet
					// make a new line starting at this junction
					RailLine newLine = new RailLine();
					newLine.setStart(junction);
					// inform the junction about the new line it is connected to
					junction.addLine(newLine);
					// add the new line to the global line pool
					this.plugin.addRailLine(newLine);
					// do a recursive call on the neighbor block
					updateRoutes(neighbor, newLine, player);
				}
			}
		} else {
			// block is a simple rail
			player.sendMessage("found new rail");
			// add the block to the line
			line.addBlock(currentBlock);
			// mark the block as visited
			visited.add(currentBlock);
			// get the next block of the track
			LinkedList<Block> next = getNextTracks(currentBlock);
			// there should be exactly one block in the list that was not visited
			if(next.size() != 2) {
				throw new TrackException("error following the track. found too many alternatives: "+next.size());
			}
			// check wheter the first or the second block in the list is the one that was not visited yet
			if(!visited.contains(next.get(0))) {
				// continue with the next block
				updateRoutes(next.get(0), line, player);
			} else if(!visited.contains(next.get(1))) {
				// continue with the next block
				updateRoutes(next.get(1), line, player);
			} else {
				//booth blocks are marked as visited. should not happen. that would be an error
				throw new TrackException("could not find a block not visited yet");
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
					if((Trainstore.isRail(neighbor.getType())) && !((x==0) && (y==0) && (z==0)) 
							&& !((Math.abs(x)==1) && (Math.abs(z)==1))) {
						list.add(neighbor);
					}
				}
			}
		}
		
		return list;
	}
}
