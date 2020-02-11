package me.deftware.mixin.mixins;

import com.mojang.blaze3d.platform.FramebufferInfo;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlStateManager.class)
public class MixinGlStateManager {

	@Shadow
	private static GlStateManager.FBOMode fboMode;

	@Inject(method = "initFramebufferSupport", at = @At("RETURN"))
	private static void initFramebufferSupport(GLCapabilities capabilities, CallbackInfoReturnable<String> ci) {
		System.out.print("Replacing DEPTH_ATTACHMENT " + FramebufferInfo.DEPTH_ATTACHMENT + " with ");
		FramebufferInfo.DEPTH_ATTACHMENT = 33306;
		System.out.println(FramebufferInfo.DEPTH_ATTACHMENT);
	}

	/**
	 * @author Deftware
	 */
	@Overwrite
	public static void renderbufferStorage(int target, int internalFormat, int width, int height) {
		System.out.print("Replacing internal format " + internalFormat + " with ");
		internalFormat = 35056;
		System.out.println(internalFormat);
		RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
		switch(fboMode) {
			case BASE:
				GL30.glRenderbufferStorage(target, internalFormat, width, height);
				break;
			case ARB:
				ARBFramebufferObject.glRenderbufferStorage(target, internalFormat, width, height);
				break;
			case EXT:
				EXTFramebufferObject.glRenderbufferStorageEXT(target, internalFormat, width, height);
		}
	}


}
