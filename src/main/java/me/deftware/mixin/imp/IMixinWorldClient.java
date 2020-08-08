package me.deftware.mixin.imp;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.entity.Entity;

import java.util.HashMap;

public interface IMixinWorldClient {

    Int2ObjectMap<Entity> getLoadedEntities();

    HashMap<Integer, IEntity> getIEntities();

}
