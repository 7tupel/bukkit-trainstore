package net.siebentupel.trainstore.signs;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CommandSign implements Sign {
	
	private static final String ROUTING_HEADER = "[tsrouting]";
	
	protected final Sign sign;
	
	public CommandSign(Block block) throws RuntimeException{
		BlockState state = block.getState();
		if (state instanceof Sign) {
			this.sign = (Sign) state;
		} else {
			throw new RuntimeException("block is not a sign");
		}
	}

	@Override
	public Block getBlock() {
		return this.sign.getBlock();
	}

	@Override
	public Chunk getChunk() {
		return this.sign.getChunk();
	}

	@Override
	public MaterialData getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getLightLevel() {
		return this.sign.getLightLevel();
	}

	@Override
	public Location getLocation() {
		return this.sign.getLocation();
	}

	@Override
	public Location getLocation(Location arg0) {
		return this.sign.getLocation(arg0);
	}

	@Override
	public byte getRawData() {
		return this.sign.getRawData();
	}

	@Override
	public Material getType() {
		return this.sign.getType();
	}

	@Override
	public int getTypeId() {
		return this.sign.getTypeId();
	}

	@Override
	public World getWorld() {
		return this.sign.getWorld();
	}

	@Override
	public int getX() {
		return this.sign.getX();
	}

	@Override
	public int getY() {
		return this.sign.getY();
	}

	@Override
	public int getZ() {
		return this.sign.getZ();
	}

	@Override
	public void setData(MaterialData arg0) {
		this.sign.setData(arg0);
	}

	@Override
	public void setRawData(byte arg0) {
		this.sign.setRawData(arg0);
	}

	@Override
	public void setType(Material arg0) {
		this.sign.setType(arg0);
	}

	@Override
	public boolean setTypeId(int arg0) {
		return this.sign.setTypeId(arg0);
	}

	@Override
	public boolean update() {
		return this.sign.update();
	}

	@Override
	public boolean update(boolean arg0) {
		return this.sign.update(arg0);
	}

	@Override
	public boolean update(boolean arg0, boolean arg1) {
		return this.sign.update(arg0, arg1);
	}

	@Override
	public List<MetadataValue> getMetadata(String arg0) {
		return this.sign.getMetadata(arg0);
	}

	@Override
	public boolean hasMetadata(String arg0) {
		return this.sign.hasMetadata(arg0);
	}

	@Override
	public void removeMetadata(String arg0, Plugin arg1) {
		this.sign.removeMetadata(arg0, arg1);
	}

	@Override
	public void setMetadata(String arg0, MetadataValue arg1) {
		this.sign.setMetadata(arg0, arg1);
	}

	@Override
	public String getLine(int index) throws IndexOutOfBoundsException {
		try {
			return this.sign.getLine(index);
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(e.getMessage());
		}
	}

	@Override
	public String[] getLines() {
		return this.sign.getLines();
	}

	@Override
	public void setLine(int index, String line)
			throws IndexOutOfBoundsException {
		try {
			this.sign.setLine(index, line);
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(e.getMessage());
		}
	}
	
	public static boolean isRoutingSign(Sign s) {
		return ROUTING_HEADER.equalsIgnoreCase(ChatColor.stripColor(s.getLine(0)));
	}

}
