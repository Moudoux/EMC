package me.deftware.mixin.mixins;

import me.deftware.client.framework.path.OSUtils;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Credit to https://github.com/juliand665/retiNO
 */
@Mixin(Window.class)
public abstract class MixinWindow {

	@Redirect(
			at = @At(
					value = "INVOKE",
					target = "Lorg/lwjgl/glfw/GLFW;glfwDefaultWindowHints()V"
			),
			method = "<init>"
	)
	private void redirectDefaultWindowHints() {
		GLFW.glfwDefaultWindowHints();
		if (OSUtils.isMac()) {
			GLFW.glfwWindowHint(GLFW.GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW.GLFW_FALSE);
		}
	}

}
