package me.deftware.mixin.mixins;

import com.mojang.blaze3d.platform.FramebufferInfo;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gl.Framebuffer;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.IntBuffer;

/*
	Enables stencil support in Minecraft
 */
@Mixin(Framebuffer.class)
public abstract class MixinFrameBuffer {

	public @Shadow int textureWidth;
	public @Shadow int textureHeight;
	private @Shadow int depthAttachment;

	@Redirect(method = "initFbo", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;texImage2D(IIIIIIIILjava/nio/IntBuffer;)V", ordinal = 0))
	private void texImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, IntBuffer pixels) {
		GlStateManager.texImage2D(
				GL11.GL_TEXTURE_2D, 0, ARBFramebufferObject.GL_DEPTH24_STENCIL8,
				this.textureWidth, this.textureHeight,
				0, ARBFramebufferObject.GL_DEPTH_STENCIL,
				GL30.GL_UNSIGNED_INT_24_8, null
		);
	}

	@Redirect(method = "initFbo", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;framebufferTexture2D(IIIII)V", ordinal = 1))
	private void framebufferTexture2D(int target, int attachment, int textureTarget, int texture, int level) {
		GlStateManager.framebufferTexture2D(
				FramebufferInfo.FRAME_BUFFER,
				GL30.GL_DEPTH_STENCIL_ATTACHMENT,
				GL11.GL_TEXTURE_2D,
				this.depthAttachment,
				0
		);
	}

}
