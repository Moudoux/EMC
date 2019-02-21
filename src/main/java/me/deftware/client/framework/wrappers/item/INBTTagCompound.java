package me.deftware.client.framework.wrappers.item;

import net.minecraft.nbt.NBTTagCompound;

public class INBTTagCompound {

    private NBTTagCompound compound;

    public INBTTagCompound(NBTTagCompound compound) {
        this.compound = compound;
    }

    public boolean isNull() { return compound == null; }

    public boolean contains(String key) {
        return compound.contains(key);
    }

    public boolean contains(String key, int i) {
        return compound.contains(key, i);
    }

    public INBTTagCompound get(String key) {
        return new INBTTagCompound(compound.getCompound(key));
    }

    public NBTTagCompound getCompound() {
        return compound;
    }

    public void setTagInfo(String key, INBTTagList list) {
        compound.put(key, list.list);
    }

}
