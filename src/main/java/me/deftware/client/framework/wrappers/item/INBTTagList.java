package me.deftware.client.framework.wrappers.item;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public class INBTTagList {

    public ListTag list;

    public INBTTagList() {
        list = new ListTag();
    }

    public void appendTag(String tag) {
        list.add(new StringTag(tag));
    }

}
