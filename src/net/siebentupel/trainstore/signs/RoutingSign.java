package net.siebentupel.trainstore.signs;

import org.bukkit.block.Block;

public class RoutingSign extends CommandSign {

	public RoutingSign(Block block) throws RuntimeException {
		super(block);
		// make sure this is a routing sign
		if(!(this.isRoutingSign(this.sign))) {
			throw new RuntimeException("sign is not a routing sign");
		}
	}

}
