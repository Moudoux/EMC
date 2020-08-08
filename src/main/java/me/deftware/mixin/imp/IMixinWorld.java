package me.deftware.mixin.imp;

import me.deftware.client.framework.wrappers.entity.ITileEntity;

import java.util.Collection;

public interface IMixinWorld {

	Collection<ITileEntity> getEmcTileEntities();
	
}
