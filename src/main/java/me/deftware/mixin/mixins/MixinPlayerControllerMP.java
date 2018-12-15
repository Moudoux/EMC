package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

	@Shadow
	private GameType currentGameType;

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public float getBlockReachDistance() {
		return (float) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "BLOCK_REACH_DISTANCE", currentGameType.isCreative() ? 5.0F : 4.5F);
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public boolean extendedReach() {
		return (boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "EXTENDED_REACH", currentGameType.isCreative());
	}

}
