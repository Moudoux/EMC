package me.deftware.mixin.mixins;

import me.deftware.client.framework.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Blocks.class)
public class MixinBlocks {

	@Inject(method = "register", at = @At("HEAD"))
	private static void register(String id, Block block, CallbackInfoReturnable<Block> ci) {
		BlockRegistry.INSTANCE.register(id, block);
	}

}
