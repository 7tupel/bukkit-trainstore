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
}
