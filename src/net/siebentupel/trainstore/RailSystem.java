package net.siebentupel.trainstore;

import java.util.HashMap;
import java.util.LinkedList;

public class RailSystem {
	/** holds all trackPoints that are part of the rail system */
	private LinkedList<TrackPoint> trackPoints;
	
	public void insertTrackPoint(TrackPoint point) {
		this.trackPoints.add(point);
	}
}
