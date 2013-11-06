package net.siebentupel.trainstore;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

public final class PlayerRightclickListener implements Listener {
	private Trainstore plugin;
	public PlayerRightclickListener(Trainstore plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void onInteraction(PlayerInteractEvent event) {
		// may need this iff the the interaction was not a block but air
		try {
	        // get the block that was clicked by the user
			Block block = event.getClickedBlock();
			// check if the block was a sign
			if((block.getType() == Material.SIGN) || (block.getType() == Material.SIGN_POST) ) {
				//event.getPlayer().setMetadata("destination", FixedMetadataValue())
				// cast block so a sign
				Sign sign = (Sign) block;
				// check if the sign is a destination sign
				if(sign.getLine(0) == "[tsdestination]") {
					// iff it is, set the new destination for the player
					this.plugin.setPlayerDestination(event.getPlayer(), sign.getLine(1));
					this.plugin.logMessage("set player destination to " + sign.getLine(1));
				}
			}
		} finally {
			
		}
    }
}
