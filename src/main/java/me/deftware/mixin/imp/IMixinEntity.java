package me.deftware.mixin.imp;

import net.minecraft.entity.data.DataTracker;

public interface IMixinEntity {

	boolean getAFlag(int flag);

	DataTracker getTracker();

}
