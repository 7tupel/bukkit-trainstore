package net.siebentupel.trainstore;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public final class MovementPredictor {

	public static Direction getDirection(Location from, Location to) {
		if(to.getBlockX() - from.getBlockX() <0) {
			return Direction.EAST;
		} else if(to.getBlockX() - from.getBlockX() >0) {
			return Direction.WEST;
		} else if(to.getBlockZ() - from.getBlockZ() <0) {
			return Direction.SOUTH;
		} else if(to.getBlockZ() - from.getBlockZ() >0) {
			return Direction.NORTH;
		} else if(to.getBlockY() - from.getBlockY() <0) {
			return Direction.UP;
		} else if(to.getBlockY() - from.getBlockY() >0) {
			return Direction.DOWN;
		}
		return null;
	}
	
	public static Boolean isJunction(Block block) {
		int railCounter = 0;
		// check all 4 blocks that can possibly have rails valid for a junction
		if(block.getRelative(1, 0, 0).getType() == Material.RAILS) {
			railCounter++;
		}
		if(block.getRelative(-1, 0, 0).getType() == Material.RAILS) {
			railCounter++;
		}
		if(block.getRelative(0, 0, 1).getType() == Material.RAILS) {
			railCounter++;
		}
		if(block.getRelative(0, 0, -1).getType() == Material.RAILS) {
			railCounter++;
		}
		// if 3 ore more neighbor blocks are rails then there is a junction 
		if(railCounter >= 3) {
			return true;
		}
		return false;
	}
	
}
