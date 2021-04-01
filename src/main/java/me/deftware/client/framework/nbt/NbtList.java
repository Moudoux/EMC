package me.deftware.client.framework.nbt;

import net.minecraft.nbt.NbtString;

/**
 * @author Deftware
 */
public class NbtList {

	private final net.minecraft.nbt.NbtList list;

	public NbtList(net.minecraft.nbt.NbtList list) {
		this.list = list;
	}

	public NbtList() {
		this(new net.minecraft.nbt.NbtList());
	}

	public void appendTag(String tag) {
		list.add(NbtString.of(tag));
	}

	public net.minecraft.nbt.NbtList getMinecraftListTag() {
		return list;
	}
	
}
