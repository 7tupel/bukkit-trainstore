package net.siebentupel.trainstore;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class PlayerRightclickListener implements Listener {
	/**
	 * reference to the plugin the listener belongs to
	 */
	private Trainstore plugin;
	
	/**
	 * default constructor. set reference to the corresponding plugin
	 * @param plugin	the plugin the listener belongs to
	 */
	public PlayerRightclickListener(Trainstore plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * handle the right click of a player
	 * @param event		the event polled when the player performs a right click
	 */
	@EventHandler
    public void onInteraction(PlayerInteractEvent event) {
		// check if the rightclick event was applied to a block
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
		}
		// get the clicked block and his state
		final Block block = event.getClickedBlock();
        final BlockState state = block.getState();
        // check if it is a sign
        if (state instanceof Sign) {
        	// get the sign
            final Sign sign = (Sign)state;
            // poll a new ClickOnSign Event
            Bukkit.getServer().getPluginManager().callEvent(new ClickOnSignEvent(sign, event.getPlayer()));

        }
    } 
}
