package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.render.camera.entity.CameraEntityMan;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class MixinCamera {

	@Inject(at = @At("HEAD"), cancellable = true, method ="getFocusedEntity")
	public void getFocusedEntity(CallbackInfoReturnable<Entity> info) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if (CameraEntityMan.isActive()) {
			info.setReturnValue(mc.player);
			info.cancel();
		}
	}

	@Inject(at = @At("HEAD"), cancellable = true, method= "isThirdPerson")
	public void isThirdPerson(CallbackInfoReturnable<Boolean> info) {
		if (CameraEntityMan.isActive()) {
			info.setReturnValue(true);
			info.cancel();
		}
	}

}
