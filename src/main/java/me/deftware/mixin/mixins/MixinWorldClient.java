package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;

import net.minecraft.client.multiplayer.WorldClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldClient.class)
public abstract class MixinWorldClient {

	@ModifyVariable(method = "showBarrierParticles", at = @At("HEAD"))
	public boolean isHoldingBarrierBlock(boolean holdingBarrier) {
		if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "render_barrier_blocks", false)) {
			return true;
		}
		return holdingBarrier;
	}

}
