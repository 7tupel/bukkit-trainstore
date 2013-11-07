package net.siebentupel.trainstore;

import net.siebentupel.trainstore.exceptions.TrackException;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class TrackStation extends TrackPoint {
	
	private final String name;
	private final String alias;

	public TrackStation(Block block) throws TrackException {
		super(block);
		// TODO Auto-generated constructor stub
		Sign sign = null;
		// check if there is a sign next to the stations block
		if(block.getRelative(1, -1, 0).getState() instanceof Sign) {
			 sign = (Sign) block.getRelative(1, -1, 0).getState();
		} else if(block.getRelative(-1, -1, 0).getState() instanceof Sign) {
			sign = (Sign) block.getRelative(-1, -1, 0).getState();
		} else if(block.getRelative(0, -1, 1).getState() instanceof Sign) {
			sign = (Sign) block.getRelative(0, -1, 1).getState();
		} else if(block.getRelative(0, -1, -1).getState() instanceof Sign) {
			sign = (Sign) block.getRelative(0, -1, -1).getState();
		} 
		// if there is a sign
		if(sign != null) {
			// check if it is a station sign and set the name of the station accordingly
			if("[tsstation]".equalsIgnoreCase(ChatColor.stripColor(sign.getLine(0)))) {
				this.name = ChatColor.stripColor(sign.getLine(1));
				this.alias = ChatColor.stripColor(sign.getLine(2));
			}
			// if not a station sign, set name and alias to empty string
			else {
				this.name = "";
				this.alias = "";
			}
		}
		// there is no sign, just set name and alias to empty string
		else {
			this.name = "";
			this.alias = "";
		}
	}

	public String getName() {
		return this.name;
	}
	
	public String getAlias(){
		return this.alias;
	}
}
