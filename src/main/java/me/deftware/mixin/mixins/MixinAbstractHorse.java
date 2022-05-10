package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSaddleCheck;
import net.minecraft.entity.passive.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractHorse.class)
public abstract class MixinAbstractHorse {

	@Shadow
	protected abstract boolean getHorseWatchableBoolean(int p_110233_1_);

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public boolean isHorseSaddled() {
		EventSaddleCheck event = new EventSaddleCheck(getHorseWatchableBoolean(4));
		event.broadcast();
		return event.isState();
	}

}
