package me.deftware.client.framework.nbt;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

/**
 * @author Deftware
 */
public class NbtList {

	private final ListTag list;

	public NbtList(ListTag list) {
		this.list = list;
	}

	public NbtList() {
		this(new ListTag());
	}

	public void appendTag(String tag) {
		list.add(StringTag.of(tag));
	}

	public ListTag getMinecraftListTag() {
		return list;
	}
	
}
