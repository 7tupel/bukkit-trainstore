package net.siebentupel.trainstore;

import org.bukkit.entity.Vehicle;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class MinecartAtJunctionEvent extends Event {
	private Vehicle cart;
	
	public MinecartAtJunctionEvent(Vehicle cart) {
		this.cart = cart;
	}
	
	public Vehicle getCart() {
		return this.cart;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
