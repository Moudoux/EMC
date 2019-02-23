package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

@Mixin(MovementInputFromOptions.class)
public class MixinMovementInputFromOptions {

	@Redirect(method = "updatePlayerMoveState", at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovementInputFromOptions;sneak:Z", opcode = GETFIELD))
	private boolean isSneaking(MovementInputFromOptions self) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Sneak);
		event.broadcast();
		if (event.isCanceled()) {
			return false;
		}
		return Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown();
	}

}
