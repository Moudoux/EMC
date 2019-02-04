package me.deftware.client.framework.wrappers.item;

import me.deftware.client.framework.utils.INonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class IItemStackHelper {

    public static void loadAllItems(INBTTagCompound compound, INonNullList<IItemStack> list) {
        NBTTagList lvt_2_1_ = compound.getCompound().getList("Items", 10);
        for(int lvt_3_1_ = 0; lvt_3_1_ < lvt_2_1_.size(); ++lvt_3_1_) {
            NBTTagCompound lvt_4_1_ = lvt_2_1_.getCompound(lvt_3_1_);
            int lvt_5_1_ = lvt_4_1_.getByte("Slot") & 255;
            if (lvt_5_1_ >= 0 && lvt_5_1_ < list.size()) {
                list.set(lvt_5_1_, IItemStack.read(new INBTTagCompound(lvt_4_1_)));
            }
        }
    }

}
