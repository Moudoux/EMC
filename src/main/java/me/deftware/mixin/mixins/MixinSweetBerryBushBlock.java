package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventDamage;
import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SweetBerryBushBlock.class)
public class MixinSweetBerryBushBlock {

    @Final
    @Shadow
    static IntProperty AGE;

    @Overwrite
    public void onEntityCollision(BlockState blockState_1, World world_1, BlockPos blockPos_1, Entity entity_1) {
        EventSlowdown eventSlowdown = new EventSlowdown(EventSlowdown.SlowdownType.BerryBush);
        EventDamage eventDamage = new EventDamage(EventDamage.DamageSource.BerryBush);
        eventSlowdown.broadcast();
        eventDamage.broadcast();
        if (entity_1 instanceof LivingEntity && entity_1.getType() != EntityType.FOX) {
            if (!eventSlowdown.isCanceled()) {
                entity_1.slowMovement(blockState_1, new Vec3d(0.800000011920929D, 0.75D, 0.800000011920929D));
            }
            if (!world_1.isClient && (Integer) blockState_1.get(AGE) > 0 && (entity_1.prevRenderX != entity_1.x || entity_1.prevRenderZ != entity_1.z)) {
                double double_1 = Math.abs(entity_1.x - entity_1.prevRenderX);
                double double_2 = Math.abs(entity_1.z - entity_1.prevRenderZ);
                if (double_1 >= 0.003000000026077032D || double_2 >= 0.003000000026077032D) {
                    if (!eventDamage.isCanceled()) {
                        entity_1.damage(DamageSource.SWEET_BERRY_BUSH, 1.0F);
                    }
                }
            }

        }
    }

}
