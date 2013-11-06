package net.siebentupel.trainstore;

import org.bukkit.block.Sign;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class ClickOnSignEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Sign sign;
	
	public ClickOnSignEvent(Sign sign) {
		this.sign = sign;
	}
	
	public Sign getSign() {
		return sign;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
