package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventKnockback;
import me.deftware.client.framework.event.events.EventSlowdown;
import me.deftware.client.framework.event.events.EventSneakingCheck;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.imp.IMixinEntity;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

@Mixin(Entity.class)
public abstract class MixinEntity implements IMixinEntity {

	@Shadow
	public boolean noClip;

	@Shadow
	public boolean isInWeb;

	@Shadow
	public boolean onGround;

	@Shadow
	public double posX;

	@Shadow
	public double posY;

	@Shadow
	public double posZ;

	@Shadow
	public double prevPosX;

	@Shadow
	public double prevPosY;

	@Shadow
	public double prevPosZ;

	@Shadow
	public double lastTickPosX;

	@Shadow
	public double lastTickPosY;

	@Shadow
	public double lastTickPosZ;

	@Shadow
	public float prevRotationYaw;

	@Shadow
	public float prevRotationPitch;

	@Shadow
	public float rotationPitch;

	@Shadow
	public float rotationYaw;

	@Shadow
	public double motionX;

	@Shadow
	public double motionY;

	@Shadow
	public double motionZ;

	@Shadow
	public abstract boolean isSneaking();

	@Shadow
	public abstract boolean isSprinting();

	@Shadow
	public abstract boolean isPassenger();

	@Shadow
	public abstract AxisAlignedBB getBoundingBox();

	@Shadow
	public abstract boolean getFlag(int flag);

	@Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;noClip:Z", opcode = GETFIELD))
	private boolean noClipCheck(Entity self) {
		boolean noClipCheck = (boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "NOCLIP", false);
		return noClip || noClipCheck && self instanceof EntityPlayerSP;
	}

	@Redirect(method = "move", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;isInWeb:Z", opcode = GETFIELD))
	private boolean webCheck(Entity self) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Web).send();
		if (event.isCanceled()) {
			isInWeb = false;
		}
		return isInWeb;
	}

	@Redirect(method = "move", at = @At(value = "INVOKE", target = "net/minecraft/entity/Entity.isSneaking()Z", opcode = GETFIELD, ordinal = 0))
	private boolean sneakingCheck(Entity self) {
		EventSneakingCheck event = new EventSneakingCheck(isSneaking()).send();
		if (event.isSneaking()) {
			return true;
		}
		return getFlag(1);
	}

	@Inject(method = "setVelocity", at = @At("HEAD"), cancellable = true)
	private void setVelocity(double x, double y, double z, CallbackInfo ci) {
		EventKnockback event = new EventKnockback(x, y, z).send();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

	@Override
	public boolean getAFlag(int flag) {
		return getFlag(flag);
	}

}
