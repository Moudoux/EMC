package me.deftware.client.framework.wrappers.item;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class INBTTagList {

    public NBTTagList list;

    public INBTTagList() {
        list = new NBTTagList();
    }

    public void appendTag(String tag) {
        list.add(new NBTTagString(tag));
    }

}
