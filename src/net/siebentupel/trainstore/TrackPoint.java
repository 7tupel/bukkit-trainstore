package net.siebentupel.trainstore;

import java.util.LinkedList;

import net.siebentupel.trainstore.exceptions.TrackException;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class TrackPoint {
	/** the block of the trackpoint */
	protected Block block;
	
	/** the floor (block beneath the block with the rail) */
	protected Block floor;
	
	protected LinkedList<RailLine> connectedLines;
	
	public TrackPoint(Block block) throws TrackException {
		// check if the block is a rail
		if(!Trainstore.isRail((block.getType()))){
			throw new TrackException("block is not a rail");
		}
		// set member variables
		this.block = block;
		this.floor = block.getRelative(0, -1, 0);
		this.connectedLines = new LinkedList<RailLine>();
	}
	
	public void addLine(RailLine line) {
		this.connectedLines.add(line);
	}
	
	public LinkedList<RailLine> getConnectedLines() {
		return this.connectedLines;
	}
	
	public Block getBlock() {
		return this.block;
	}
	
	public LinkedList<TrackStation> getAttachedStations() {
		LinkedList<TrackStation> stations = new LinkedList<TrackStation>();
		// go tzhrough all connected lines
		for(RailLine line : connectedLines) {
			// check if this is a trackStation or a TrackRouter
			if(this instanceof TrackJunction) {
				// check if the start or the end of the line is a station
				if(line.getStart() instanceof TrackStation) {
					stations.add((TrackStation)line.getStart());
				}
				if(line.getEnd() instanceof TrackStation) {
					stations.add((TrackStation)line.getEnd());
				}
			}
			// this is a TrackStation
			else if(this instanceof TrackStation) {
				// make sure the line start/end is a TrackStation as well and not equal to this
				if((line.getStart() instanceof TrackStation) && !(((TrackStation)this).equals((TrackStation)line.getStart()))){
					stations.add((TrackStation)line.getStart());
				}
				if((line.getEnd() instanceof TrackStation) && !(((TrackStation)this).equals((TrackStation)line.getEnd()))){
					stations.add((TrackStation)line.getEnd());
				}
			}
		}
		return stations;
	}
	
	public LinkedList<TrackJunction> getAttachedJunctions() {
		LinkedList<TrackJunction> junctions = new LinkedList<TrackJunction>();
		// go through all connected lines
		for(RailLine line : connectedLines) {
			if(this instanceof TrackStation) {
				// check if the start or the end of the line is a station
				if((line.getStart() instanceof TrackJunction) && !(this.equals((TrackJunction)line.getStart()))) {
					junctions.add((TrackJunction)line.getStart());
				}
				if((line.getEnd() instanceof TrackJunction)  && !(this.equals((TrackJunction)line.getEnd()))) {
					junctions.add((TrackJunction)line.getEnd());
				}
			}
		}
		return junctions;
	}

	
}
