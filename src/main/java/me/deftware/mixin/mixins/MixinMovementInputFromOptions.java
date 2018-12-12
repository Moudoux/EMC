package me.deftware.mixin.mixins;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInputFromOptions;

@Mixin(MovementInputFromOptions.class)
public class MixinMovementInputFromOptions {

	@Redirect(method = "updatePlayerMoveState", at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovementInputFromOptions;sneak:Z", opcode = GETFIELD))
	private boolean isSneaking(MovementInputFromOptions self) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Sneak).send();
		if (event.isCanceled()) {
			return false;
		}
		return Minecraft.getInstance().gameSettings.keyBindSneak.isKeyDown();
	}

}
