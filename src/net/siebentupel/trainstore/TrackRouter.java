package net.siebentupel.trainstore;

import net.siebentupel.trainstore.exceptions.TrackException;

import org.bukkit.block.Block;

public class TrackRouter extends TrackPoint {
	
	private final int cardinality;

	public TrackRouter(Block block) throws TrackException {
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

}
