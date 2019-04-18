package me.deftware.client.framework.wrappers.world;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.SpawnHelper;

public class IWorldEntitySpawner {

    public static boolean canCreatureTypeSpawnAtLocation(ISpawnPlacementType placementType, IWorld world, IBlockPos pos, boolean passive) {
        return SpawnHelper.canSpawn(placementType.toMCType(), world.getWorld(), pos.getPos(), passive ? EntityType.PIG : EntityType.ZOMBIE);
    }

    public enum ISpawnPlacementType {
        ON_GROUND,
        IN_WATER;

        public SpawnRestriction.Location toMCType() {
            switch (this) {
                case ON_GROUND:
                    return SpawnRestriction.Location.ON_GROUND;
                case IN_WATER:
                    return SpawnRestriction.Location.IN_WATER;
                default:
                    return null;
            }
        }

    }

}
