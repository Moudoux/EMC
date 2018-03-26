package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventNotePlayed;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityNote.class)
public class MixinTileEntityNote {

	@Shadow
	private byte note;

	@Inject(method = "triggerNote", at = @At("HEAD"))
	private void triggerNote(World worldIn, BlockPos posIn, CallbackInfo ci) {
		new EventNotePlayed(note).send();
	}

}
