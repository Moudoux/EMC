package me.deftware.mixin.mixins;

import com.mojang.blaze3d.platform.FramebufferInfo;
import com.mojang.blaze3d.platform.GlStateManager;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import org.lwjgl.opengl.GLCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlStateManager.class)
public class MixinGlStateManager {

	@Inject(method = "initFramebufferSupport", at = @At("RETURN"))
	private static void initFramebufferSupport(GLCapabilities capabilities, CallbackInfoReturnable<String> ci) {
		Bootstrap.logger.debug("Replacing DEPTH_ATTACHMENT " + FramebufferInfo.DEPTH_ATTACHMENT + " with 33306");
		FramebufferInfo.DEPTH_ATTACHMENT = 33306;
	}

	@ModifyVariable(method = "renderbufferStorage", at = @At("HEAD"), ordinal = 1)
	private static int renderbufferStorage(int internalFormat) {
		Bootstrap.logger.debug("Replacing internal format " + internalFormat + " with 35056");
		internalFormat = 35056;

		return internalFormat;
	}


}
