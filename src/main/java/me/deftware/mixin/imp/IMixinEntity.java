package me.deftware.mixin.imp;

import net.minecraft.entity.data.DataTracker;

public interface IMixinEntity {

	boolean getAFlag(int flag);

	void setInPortal(boolean inPortal);

	DataTracker getTracker();

}
