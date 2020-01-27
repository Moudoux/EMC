package me.deftware.mixin.mixins;

import net.minecraft.block.SoulSandBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoulSandBlock.class)
public class MixinBlockSoulSand {

    /*
    @Overwrite
    public void onEntityCollision(BlockState blockState_1, World world_1, BlockPos blockPos_1, Entity entity_1) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Soulsand);
        event.broadcast();
        if (event.isCanceled()) {
            return;
        }
        entity_1.setVelocity(entity_1.getVelocity().multiply(0.4D, 1.0D, 0.4D));
    }*/

}
