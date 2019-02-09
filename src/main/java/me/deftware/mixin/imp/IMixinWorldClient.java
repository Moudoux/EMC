package me.deftware.mixin.imp;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;

public interface IMixinWorldClient {

    Int2ObjectMap<Entity> getLoadedEntities();

}
