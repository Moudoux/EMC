package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockSoulSand.class)
public class MixinBlockSoulSand {

	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Soulsand).send();
		if (event.isCanceled()) {
			return;
		}
		entityIn.motionX *= 0.4D;
		entityIn.motionZ *= 0.4D;
	}

}
