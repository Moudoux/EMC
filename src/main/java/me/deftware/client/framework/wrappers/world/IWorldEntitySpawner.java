package me.deftware.client.framework.wrappers.world;

import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.WorldEntitySpawner;

public class IWorldEntitySpawner {

    public static boolean canCreatureTypeSpawnAtLocation(ISpawnPlacementType placementType, IWorld world, IBlockPos pos, boolean passive) {
        return WorldEntitySpawner.canCreatureTypeSpawnAtLocation(placementType.toMCType(), world.getWorld(), pos.getPos(), passive ? EntityType.PIG : EntityType.ZOMBIE);
    }

    public enum ISpawnPlacementType {
        ON_GROUND,
        IN_WATER;

        public EntitySpawnPlacementRegistry.SpawnPlacementType toMCType() {
            switch(this) {
                case ON_GROUND:
                    return EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND;
                case IN_WATER:
                    return EntitySpawnPlacementRegistry.SpawnPlacementType.IN_WATER;
                default:
                    return null;
            }
        }

    }

}
