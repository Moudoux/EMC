package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinTimer;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

@Mixin(Timer.class)
public class MixinTimer implements IMixinTimer {

	@Shadow
	private float tickLength;

	private float speed = 1;

	@Redirect(method = "updateTimer", at = @At(value = "FIELD", target = "Lnet/minecraft/util/Timer;tickLength:F", opcode = GETFIELD))
	private float getTickLength(Timer self) {
		return tickLength / speed;
	}

	@Override
	public void setTimerSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public float getTimerSpeed() {
		return speed;
	}

}
