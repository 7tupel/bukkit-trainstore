package net.siebentupel.trainstore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import net.siebentupel.trainstore.exceptions.TrackException;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class TrackJunction extends TrackPoint {
	
	private final int cardinality;
	
	private HashMap<TrackStation, Direction> localRoutingTable = new HashMap<TrackStation, Direction>();

	public TrackJunction(Block block) throws TrackException {
		super(block);
		// TODO Auto-generated constructor stub
		// set the cardinality of the junction
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
		this.cardinality = counter;
	}
	
	public int getCardinality() {
		return this.cardinality;
	}
	
	/**
	 * create a local routing entry for all stations that are directly connected to the junction
	 */
	void updateRoutingTable() {
		// get all stations that are directly connected to this junction
		LinkedList<TrackStation> stations = this.getAttachedStations();
		// go through all directly attached stations and create a route for them+
		BlockFace face = null;
		for(TrackStation station : stations) {
			// only add the new routing entry if there is none for the station yet
			if(!localRoutingTable.containsKey(station)) {
				// get the line that connects the junction and the station
				RailLine line = station.getConnectedLines().getFirst();
				// check if the junction is whether the start or end of the line
				
				if((line.getStart() instanceof TrackJunction) && (((TrackJunction)line.getStart()).equals(this))) {
					face = this.block.getFace(line.getStartBlock());
				} else if((line.getEnd() instanceof TrackJunction) && (((TrackJunction)line.getEnd()).equals(this))) {
					face = this.block.getFace(line.getEndBlock());
				}
				switch(face) {
				case NORTH:
					localRoutingTable.put(station, Direction.NORTH);
					break;
				case SOUTH:
					localRoutingTable.put(station, Direction.SOUTH);
					break;
				case EAST:
					localRoutingTable.put(station, Direction.EAST);
					break;
				case WEST:
					localRoutingTable.put(station, Direction.WEST);
					break;
				}
			}
		}
	}
	
	void updateRoutingTable2(Player player) {
		BlockFace face = null;
		TrackJunction junction = null;
		// go trough all lines connected to this junction
		for(RailLine line : this.connectedLines) {
			// get the connected junction and the face to it
			if((line.getStart() instanceof TrackJunction) && !(((TrackJunction)line.getStart()).equals(this))) {
				junction = (TrackJunction)line.getStart();
				face = this.block.getFace(line.getEndBlock());
			} else if((line.getEnd() instanceof TrackJunction) && !(((TrackJunction)line.getEnd()).equals(this))) {
				junction = (TrackJunction)line.getEnd();
				face = this.block.getFace(line.getStartBlock());
			}
			// get all routing table entries of the neighbor junction
			if((junction != null) && (face != null)) {
				Set<TrackStation> set = junction.getLocalRoutingTable().keySet();
				
				for(TrackStation s : set) {
					// if there is no enty in the local routing table for this station
					if(!this.localRoutingTable.containsKey(s)) {
						switch(face) {
						case NORTH:
							localRoutingTable.put(s, Direction.NORTH);
							break;
						case SOUTH:
							localRoutingTable.put(s, Direction.SOUTH);
							break;
						case EAST:
							localRoutingTable.put(s, Direction.EAST);
							break;
						case WEST:
							localRoutingTable.put(s, Direction.WEST);
							break;
						}
					}
				}
			}
		}
	}
	
	
	public HashMap<TrackStation, Direction> getLocalRoutingTable() {
		return this.localRoutingTable;
	}
	
	
	public Direction getDirection(TrackStation station) {
		return this.localRoutingTable.get(station);
	}
}
