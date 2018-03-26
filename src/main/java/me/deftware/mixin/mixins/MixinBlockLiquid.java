package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventIsPassable;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.world.IBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquid {

	@Inject(method = "getCollisionBoundingBox", at = @At("HEAD"), cancellable = true)
	private void getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<AxisAlignedBB> ci) {
		ci.setReturnValue((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "liquid_aabb_solid", false)
				? Block.FULL_BLOCK_AABB
				: Block.NULL_AABB);
	}

	@Inject(method = "isPassable", at = @At("HEAD"), cancellable = true)
	private void isPassable(IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
		Material m = worldIn.getBlockState(pos).getBlock().getDefaultState().getMaterial();
		EventIsPassable event = new EventIsPassable(new IBlock(worldIn.getBlockState(pos).getBlock()), m != Material.LAVA).send();
		ci.setReturnValue(event.isPassable());
	}

}
