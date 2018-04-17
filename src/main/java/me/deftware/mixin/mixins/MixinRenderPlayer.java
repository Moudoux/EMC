package me.deftware.mixin.mixins;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.deftware.client.framework.event.events.EventSetModelVisibilities;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer {

	@Redirect(method = "setModelVisibilities", at = @At(value = "INVOKE", target = "net/minecraft/client/entity/AbstractClientPlayer.isSpectator()Z", opcode = GETFIELD))
	private boolean setModelVisibilities_isSpectator(AbstractClientPlayer self) {
		EventSetModelVisibilities event = new EventSetModelVisibilities(self.isSpectator()).send();
		return event.isSpectator();
	}

}
