package me.deftware.mixin.imp;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import me.deftware.client.framework.entity.Entity;


public interface IMixinWorldClient {

    Int2ObjectMap<Entity> getLoadedEntitiesAccessor();

}
