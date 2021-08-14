package me.deftware.mixin.mixins.game;

import me.deftware.client.framework.minecraft.ClientOptions;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameOptions.class)
public class MixinGameOptions implements ClientOptions {

	@Override
	public int getViewDistance() {
		return ((GameOptions) (Object) this).viewDistance;
	}

	@Override
	public boolean isFullScreen() {
		return ((GameOptions) (Object) this).fullscreen;
	}

}
