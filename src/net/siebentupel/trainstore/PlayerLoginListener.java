package net.siebentupel.trainstore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public final class PlayerLoginListener implements Listener {
	/**
	 * reference to the plugin the listener belongs to
	 */
	private Trainstore plugin;
	
	public PlayerLoginListener(Trainstore plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void onLogin(PlayerLoginEvent event) {
        this.plugin.setPlayerDestination(event.getPlayer(), "default");
    }
}
