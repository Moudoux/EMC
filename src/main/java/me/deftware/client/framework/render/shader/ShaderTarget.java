package me.deftware.client.framework.render.shader;

import lombok.Getter;
import lombok.Setter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider;

import java.util.function.Predicate;

/**
 * @author Deftware
 */
public enum ShaderTarget {

	PLAYER, ENTITY, DROPPED, STORAGE;

	private @Getter OutlineVertexConsumerProvider outlineVertexConsumerProvider;
	private @Setter @Getter Predicate<String> predicate = type -> true;
	private @Getter @Setter boolean enabled = false;
	private @Getter Framebuffer framebuffer;
	private @Setter Shader shader;

	public void renderBuffer() {
		if (framebuffer != null && shader != null && enabled) {
			// Bind shader
			shader.bind();
			shader.setupUniforms();
			// Draw buffer
			framebuffer.draw(MinecraftClient.getInstance().getWindow().getFramebufferWidth(), MinecraftClient.getInstance().getWindow().getFramebufferHeight(), false);
			// Unbind shader
			shader.unbind();
		}
	}

	public void init(VertexConsumerProvider.Immediate entityVertexConsumers) {
		framebuffer = new SimpleFramebuffer(MinecraftClient.getInstance().getFramebuffer().viewportWidth, MinecraftClient.getInstance().getFramebuffer().viewportHeight, true, MinecraftClient.IS_SYSTEM_MAC);
		outlineVertexConsumerProvider = new OutlineVertexConsumerProvider(entityVertexConsumers);
	}

	public void clear() {
		if (framebuffer != null) framebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
	}

	public void onResized(int width, int height) {
		if (framebuffer != null) framebuffer.resize(width, height, MinecraftClient.IS_SYSTEM_MAC);
	}

}
