package me.deftware.mixin.imp;

import me.deftware.client.framework.entity.block.TileEntity;

import java.util.Collection;

public interface IMixinWorld {

	Collection<TileEntity> getLoadedTilesAccessor();
	
}
