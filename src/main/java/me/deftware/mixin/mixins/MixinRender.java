package me.deftware.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.deftware.client.framework.event.events.EventNametagRender;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(Render.class)
public class MixinRender<T extends Entity> {

	@Inject(method = "renderLivingLabel", at = @At("HEAD"), cancellable = true)
	private void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance,
			CallbackInfo cb) {
		EventNametagRender event = new EventNametagRender(entityIn instanceof EntityPlayer).send();
		if (event.isCanceled()) {
			cb.cancel();
		}
	}

}
