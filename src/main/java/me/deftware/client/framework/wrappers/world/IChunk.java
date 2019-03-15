package me.deftware.client.framework.wrappers.world;

import net.minecraft.world.EnumLightType;
import net.minecraft.world.chunk.Chunk;

public class IChunk {

    private final Chunk chunk;

    public IChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public IWorld getWorld() {
        return new IWorld(chunk.getWorld());
    }

    public int getLightFor(IEnumLightType lightType, IBlockPos pos) {
        return chunk.getLightFor(lightType.getMCType(), pos.getPos());
    }

    public Chunk getChunk() {
        return chunk;
    }

    public enum IEnumLightType {
        SKY(15),
        BLOCK(0);

        public final int defaultLightValue;

        private IEnumLightType(int defaultLightValueIn)
        {
            this.defaultLightValue = defaultLightValueIn;
        }

        public EnumLightType getMCType() {
            switch(this) {
                case SKY:
                    return EnumLightType.SKY;
                case BLOCK:
                    return EnumLightType.BLOCK;
                default:
                    return null;
            }
        }
    }

}
