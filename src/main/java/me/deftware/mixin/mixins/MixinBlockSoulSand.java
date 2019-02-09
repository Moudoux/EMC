package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SoulSandBlock.class)
public class MixinBlockSoulSand {

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public void onEntityCollision(BlockState blockState_1, World world_1, BlockPos blockPos_1, Entity entity_1) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Soulsand).send();
        if (event.isCanceled()) {
            return;
        }
        entity_1.velocityX *= 0.4D;
        entity_1.velocityY *= 0.4D;
    }

}
