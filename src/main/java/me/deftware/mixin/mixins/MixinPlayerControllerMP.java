package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventExtendedReach;
import me.deftware.client.framework.event.events.EventGetReachDistance;
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
		EventGetReachDistance event = new EventGetReachDistance(currentGameType.isCreative() ? 5.0F : 4.5F).send();
		return event.getDistance();
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public boolean extendedReach() {
		EventExtendedReach event = new EventExtendedReach(currentGameType.isCreative()).send();
		return event.isState();
	}

}
