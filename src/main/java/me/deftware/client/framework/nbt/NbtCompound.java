package me.deftware.client.framework.nbt;

import net.minecraft.nbt.CompoundTag;

/**
 * @author Deftware
 */
public class NbtCompound {

	private final CompoundTag compound;

	public NbtCompound(CompoundTag compound) {
		this.compound = compound;
	}

	public NbtCompound() {
		this.compound = new CompoundTag();
	}

	public boolean isValid() {
		return compound != null;
	}

	public boolean contains(String key) {
		return compound.contains(key);
	}

	public boolean contains(String key, int type) {
		return compound.contains(key, type);
	}

	public NbtCompound get(String key) {
		return new NbtCompound(compound.getCompound(key));
	}

	public CompoundTag getMinecraftCompound() {
		return compound;
	}

	public void setTagInfo(String key, NbtList list) {
		compound.put(key, list.getMinecraftListTag());
	}

}
