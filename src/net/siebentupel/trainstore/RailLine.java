package net.siebentupel.trainstore;

import java.util.LinkedList;

import org.bukkit.block.Block;

public class RailLine {
	/** a list of all blocks of this track */
	private LinkedList<Block> railBlocks = new LinkedList<Block>();
	
	private TrackPoint start = null;
	
	private TrackPoint end = null;
	
	
	public TrackPoint getStart() {
		return start;
	}

	public void setStart(TrackPoint start) {
		this.start = start;
	}

	public TrackPoint getEnd() {
		return end;
	}

	public void setEnd(TrackPoint end) {
		this.end = end;
	}
	
	public int getWeight() {
		return railBlocks.size();
	}
	
	public Block getStartBlock() {
		return railBlocks.getFirst();
	}
	
	public Block getEndBlock() {
		return railBlocks.getLast();
	}
	
	public void addBlock(Block block) {
		this.railBlocks.add(block);
	}
	
}
