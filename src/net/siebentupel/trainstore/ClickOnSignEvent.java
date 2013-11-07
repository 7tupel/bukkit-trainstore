package net.siebentupel.trainstore;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class ClickOnSignEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	/** the sign the player clicked */
	private final Sign sign;
	/** the player who clicked on the sign */
	private final Player player;
	
	/**
	 * default constructor
	 * @param sign		the sign that was clicked
	 * @param player	the player who clicked on the sign
	 */
	public ClickOnSignEvent(Sign sign, Player player) {
		this.sign = sign;
		this.player = player;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

    public static HandlerList getHandlerList() {
        return handlers;
    }

	public Sign getSign() {
		return sign;
	}
    
    public Player getPlayer() {
    	return this.player;
    }

}
