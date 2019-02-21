package me.deftware.client.framework.wrappers.item;

import net.minecraft.nbt.CompoundTag;

public class INBTTagCompound {

    private CompoundTag compound;

    public INBTTagCompound(CompoundTag compound) {
        this.compound = compound;
    }

    public boolean isNull() {
        return compound == null;
    }

    public boolean contains(String key) {
        return compound.containsKey(key);
    }

    public boolean contains(String key, int i) {
        return compound.containsKey(key, i);
    }

    public INBTTagCompound get(String key) {
        return new INBTTagCompound(compound.getCompound(key));
    }

    public CompoundTag getCompound() {
        return compound;
    }

    public void setTagInfo(String key, INBTTagList list) {
        compound.put(key, list.list);
    }

}

