package me.deftware.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockSoulSand.class)
public class MixinBlockSoulSand {

	/**
	 * @author Deftware
	 * @reason
	 */
	@Overwrite
	public void onCollisionWithEntity(IBlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity entityIn) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Soulsand).send();
		if (event.isCanceled()) {
			return;
		}
		entityIn.motionX *= 0.4D;
		entityIn.motionZ *= 0.4D;
	}

}
