package me.deftware.client.framework.nbt;

import java.util.UUID;

/**
 * @author Deftware
 */
public class NbtCompound {

	private final net.minecraft.nbt.NbtCompound compound;

	public NbtCompound(net.minecraft.nbt.NbtCompound compound) {
		this.compound = compound;
	}

	public NbtCompound() {
		this.compound = new net.minecraft.nbt.NbtCompound();
	}

	public boolean isValid() {
		return compound != null;
	}

	public boolean contains(String key) {
		return compound.contains(key);
	}

	public boolean getBoolean(String key) {
		return getMinecraftCompound().getBoolean(key);
	}

	public UUID getUUID(String key) {
		if (getMinecraftCompound().containsUuid(key))
			return getMinecraftCompound().getUuid(key);
		return null;
	}

	public boolean contains(String key, int type) {
		return compound.contains(key, type);
	}

	public NbtCompound get(String key) {
		return new NbtCompound(compound.getCompound(key));
	}

	public net.minecraft.nbt.NbtCompound getMinecraftCompound() {
		return compound;
	}

	public void setTagInfo(String key, NbtList list) {
		compound.put(key, list.getMinecraftListTag());
	}

}
