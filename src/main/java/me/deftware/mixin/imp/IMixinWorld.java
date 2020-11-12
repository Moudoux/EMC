package me.deftware.mixin.imp;

import me.deftware.client.framework.entity.block.TileEntity;
import net.minecraft.block.entity.BlockEntity;

import java.util.Collection;
import java.util.HashMap;

public interface IMixinWorld {

	Collection<TileEntity> getLoadedTilesAccessor();

	HashMap<Long, BlockEntity> getInternalLongToBlockEntity();
	
}
