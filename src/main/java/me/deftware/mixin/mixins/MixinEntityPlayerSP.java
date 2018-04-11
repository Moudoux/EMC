package me.deftware.mixin.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.deftware.client.framework.event.events.EventChatSend;
import me.deftware.client.framework.event.events.EventClientCommand;
import me.deftware.client.framework.event.events.EventIRCMessage;
import me.deftware.client.framework.event.events.EventPlayerWalking;
import me.deftware.client.framework.event.events.EventSlowdown;
import me.deftware.client.framework.event.events.EventUpdate;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.mixin.imp.IMixinEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.AxisAlignedBB;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends MixinEntity implements IMixinEntityPlayerSP {

	@Shadow
	private boolean prevOnGround;

	@Shadow
	private boolean serverSprintState;

	@Shadow
	private boolean serverSneakState;

	@Shadow
	private boolean autoJumpEnabled;

	@Shadow
	private float lastReportedYaw;

	@Shadow
	private float lastReportedPitch;

	@Shadow
	private double lastReportedPosX;

	@Shadow
	private double lastReportedPosY;

	@Shadow
	private double lastReportedPosZ;

	@Shadow
	private int positionUpdateTicks;

	@Shadow
	private float horseJumpPower;

	@Shadow
	protected abstract boolean isCurrentViewEntity();

	@Shadow
	public abstract boolean isHandActive();

	@Shadow
	@Final
	public NetHandlerPlayClient connection;

	@Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.isHandActive()Z", ordinal = 0))
	private boolean itemUseSlowdownEvent(EntityPlayerSP self) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Item_Use).send();
		if (event.isCanceled()) {
			return false;
		}
		return isHandActive();
	}

	@Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "net/minecraft/util/FoodStats.getFoodLevel()I"))
	private int hungerSlowdownEvent(FoodStats self) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Hunger).send();
		if (event.isCanceled()) {
			return 7;
		}
		return self.getFoodLevel();
	}

	@Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "net/minecraft/entity/EntityLivingBase.isPotionActive(Lnet/minecraft/potion/Potion;)Z"))
	private boolean blindlessSlowdownEvent(EntityLivingBase self) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Blindness).send();
		if (event.isCanceled()) {
			return false;
		}
		return self.isPotionActive(MobEffects.BLINDNESS);
	}

	@Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
	private void onUpdate(CallbackInfo ci) {
		EventUpdate event = new EventUpdate(posX, posY, posZ, rotationYaw, rotationPitch, onGround).send();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public void sendChatMessage(String message) {
		String trigger = Bootstrap.isTrigger(message);
		if (!trigger.equals("")) {
			if (message.startsWith(trigger + "say")) {
				if (!message.contains(" ")) {
					ChatProcessor.printClientMessage("Invalid syntax, please use: §b" + trigger + "say <Message>");
					return;
				}
				connection.sendPacket(new CPacketChatMessage(message.substring((trigger + "say ").length())));
				return;
			}
			new EventClientCommand(message, trigger).send();
			return;
		} else if (message.startsWith("#")) {
			if (message.startsWith("# ")) {
				message = message.substring(2);
			} else {
				message = message.substring(1);
			}
			if (message.equals("")) {
				ChatProcessor.printClientMessage("Invalid syntax, please use: §b# <Message>");
				return;
			}
			new EventIRCMessage(message).send();
			return;
		}
		EventChatSend event = new EventChatSend(message).send();
		if (!event.isCanceled()) {
			connection.sendPacket(new CPacketChatMessage(event.getMessage()));
		}
	}

	@Override
	public void setHorseJumpPower(float height) {
		horseJumpPower = height;
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	private void onUpdateWalkingPlayer() {
		EventPlayerWalking event = new EventPlayerWalking(posX, posY, posZ, rotationYaw, rotationPitch, onGround)
				.send();
		if (event.isCanceled()) {
			return;
		}
		boolean flag = isSprinting();

		if (flag != serverSprintState) {
			if (flag) {
				connection.sendPacket(new CPacketEntityAction((EntityPlayerSP) (Object) this,
						CPacketEntityAction.Action.START_SPRINTING));
			} else {
				connection.sendPacket(new CPacketEntityAction((EntityPlayerSP) (Object) this,
						CPacketEntityAction.Action.STOP_SPRINTING));
			}

			serverSprintState = flag;
		}

		boolean flag1 = isSneaking();

		if (flag1 != serverSneakState) {
			if (flag1) {
				connection.sendPacket(new CPacketEntityAction((EntityPlayerSP) (Object) this,
						CPacketEntityAction.Action.START_SNEAKING));
			} else {
				connection.sendPacket(new CPacketEntityAction((EntityPlayerSP) (Object) this,
						CPacketEntityAction.Action.STOP_SNEAKING));
			}

			serverSneakState = flag1;
		}

		if (isCurrentViewEntity()) {
			AxisAlignedBB axisalignedbb = getEntityBoundingBox();
			double d0 = posX - lastReportedPosX;
			double d1 = event.getPosY() - lastReportedPosY;
			double d2 = posZ - lastReportedPosZ;
			double d3 = event.getRotationYaw() - lastReportedYaw;
			double d4 = event.getRotationPitch() - lastReportedPitch;
			++positionUpdateTicks;
			boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || positionUpdateTicks >= 20;
			boolean flag3 = d3 != 0.0D || d4 != 0.0D;

			if (isRiding()) {
				connection.sendPacket(new CPacketPlayer.PositionRotation(motionX, -999.0D, motionZ,
						event.getRotationYaw(), event.getRotationPitch(), event.isOnGround()));
				flag2 = false;
			} else if ((flag2 && flag3)) {
				connection.sendPacket(new CPacketPlayer.PositionRotation(posX, event.getPosY(), posZ,
						event.getRotationYaw(), event.getRotationPitch(), event.isOnGround()));
			} else if (flag2) {
				connection.sendPacket(new CPacketPlayer.Position(posX, event.getPosY(), posZ, event.isOnGround()));
			} else if (flag3) {
				connection.sendPacket(new CPacketPlayer.Rotation(event.getRotationYaw(), event.getRotationPitch(),
						event.isOnGround()));
			} else if (prevOnGround != onGround) {
				connection.sendPacket(new CPacketPlayer(event.isOnGround()));
			}

			if (flag2) {
				lastReportedPosX = posX;
				lastReportedPosY = axisalignedbb.minY;
				lastReportedPosZ = posZ;
				positionUpdateTicks = 0;
			}

			if (flag3) {
				lastReportedYaw = rotationYaw;
				lastReportedPitch = rotationPitch;
			}

			prevOnGround = onGround;
			autoJumpEnabled = Minecraft.getMinecraft().gameSettings.autoJump;
		}
	}

}
