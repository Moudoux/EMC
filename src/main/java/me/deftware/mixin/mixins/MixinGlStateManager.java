package me.deftware.mixin.mixins;

import com.mojang.blaze3d.platform.FramebufferInfo;
import com.mojang.blaze3d.platform.GlStateManager;
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
		FramebufferInfo.DEPTH_ATTACHMENT = 33306;
	}

	@ModifyVariable(method = "renderbufferStorage", at = @At("HEAD"), ordinal = 1)
	private static int renderbufferStorage(int internalFormat) {
		return 35056;
	}


}
