package net.siebentupel.trainstore;

import org.bukkit.block.Block;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class MinecartAtJunctionEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final Vehicle cart;
	private final Block fromLocation;
	private final Block toLocation;
	
	public MinecartAtJunctionEvent(Vehicle cart, Block fromLocation, Block toLocation) {
		this.cart = cart;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
	}
	
	
	
	public Vehicle getCart() {
		return this.cart;
	}
	
	public Block getFromLocation() {
		return this.fromLocation;
	}
	
	public Block getToLocation() {
		return this.toLocation;
	}


	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
