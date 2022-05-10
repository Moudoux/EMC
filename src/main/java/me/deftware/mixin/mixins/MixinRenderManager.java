package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinRenderManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderManager.class)
public class MixinRenderManager implements IMixinRenderManager {

	@Shadow
	private double renderPosX;

	@Shadow
	private double renderPosY;

	@Shadow
	private double renderPosZ;

	public double getRenderPosX() {
		return renderPosX;
	}

	public double getRenderPosY() {
		return renderPosY;
	}

	public double getRenderPosZ() {
		return renderPosZ;
	}

}
