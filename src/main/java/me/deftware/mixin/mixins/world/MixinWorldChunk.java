package me.deftware.mixin.mixins.world;

import me.deftware.mixin.imp.IMixinWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(WorldChunk.class)
public class MixinWorldChunk {

	@Shadow
	@Final
	World world;

	@Inject(method = "updateTicker", at = @At("HEAD"))
	private <T extends BlockEntity> void test(T blockEntity, CallbackInfo ci) {
		BlockState blockState = blockEntity.getCachedState();
		if (blockState.getBlockEntityTicker(this.world, blockEntity.getType()) != null) {
			long pos = blockEntity.getPos().asLong();
			HashMap<Long, BlockEntity> entities = ((IMixinWorld) world).getInternalLongToBlockEntity();
			entities.put(pos, blockEntity);
		}
	}

}
