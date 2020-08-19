package me.deftware.mixin.mixins;

import me.deftware.client.framework.render.camera.GameCamera;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityHitResult.class)
public class MixinEntityHitResult {

	@Shadow
	@Final
	private Entity entity;

	@Overwrite
	public Entity getEntity() {
		if (this.entity == MinecraftClient.getInstance().player) {
			return null;
		}
		return this.entity;
	}

}
