package me.deftware.mixin.mixins;

import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.event.events.*;
import me.deftware.client.framework.utils.ChatColor;
import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.client.framework.wrappers.IChat;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

	@Redirect(method = "livingTick", at = @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.isHandActive()Z", ordinal = 0))
	private boolean itemUseSlowdownEvent(EntityPlayerSP self) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Item_Use);
		event.broadcast();
		if (event.isCanceled()) {
			return false;
		}
		return isHandActive();
	}

	@Redirect(method = "livingTick", at = @At(value = "INVOKE", target = "net/minecraft/util/FoodStats.getFoodLevel()I"))
	private int hungerSlowdownEvent(FoodStats self) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Hunger);
		event.broadcast();
		if (event.isCanceled()) {
			return 7;
		}
		return self.getFoodLevel();
	}

	@Redirect(method = "livingTick", at = @At(value = "INVOKE", target = "net/minecraft/entity/EntityLivingBase.isPotionActive(Lnet/minecraft/potion/Potion;)Z"))
	private boolean blindlessSlowdownEvent(EntityLivingBase self) {
		EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Blindness);
		event.broadcast();
		if (event.isCanceled()) {
			return false;
		}
		return self.isPotionActive(MobEffects.BLINDNESS);
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void tick(CallbackInfo ci) {
		EventUpdate event = new EventUpdate(posX, posY, posZ, rotationYaw, rotationPitch, onGround);
		event.broadcast();
		if (event.isCanceled()) {
			ci.cancel();
		}
	}

	@Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
	public void sendChatMessage(String message, CallbackInfo ci) {
		String trigger = CommandRegister.getCommandTrigger();
		if (message.startsWith(trigger) && !trigger.equals("")) {
			try {
				if (message.startsWith(trigger + "say")) {
					if (!message.contains(" ")) {
						ChatProcessor.printClientMessage(
								"Invalid syntax, please use: " + ChatColor.AQUA + trigger + "say <Message>");
						return;
					}
					connection.sendPacket(new CPacketChatMessage(message.substring((trigger + "say ").length())));
					ci.cancel();
					return;
				}
				CommandRegister.getDispatcher().execute(message.substring(CommandRegister.getCommandTrigger().length()), Minecraft.getInstance().player.getCommandSource());
			} catch (Exception ex) {
				ex.printStackTrace();
				IChat.sendClientMessage(ex.getMessage());
			}
			ci.cancel();
		} else if (message.startsWith("#")) {
			message = message.startsWith("# ") ? message.substring(2) : message.substring(1);
			if (message.equals("")) {
				ChatProcessor.printClientMessage("Invalid syntax, please use: " + ChatColor.AQUA + "# <Message>");
				ci.cancel();
				return;
			}
			new EventIRCMessage(message).broadcast();
			ci.cancel();
			return;
		}
		EventChatSend event = new EventChatSend(message);
		event.broadcast();
		if (event.isCanceled()) {
			ci.cancel();
		} else if (!event.getMessage().equals(message)) {
			connection.sendPacket(new CPacketChatMessage(event.getMessage()));
			ci.cancel();
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
		EventPlayerWalking event = new EventPlayerWalking(posX, posY, posZ, rotationYaw, rotationPitch, onGround);
		event.broadcast();
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
			AxisAlignedBB axisalignedbb = getBoundingBox();
			double d0 = posX - lastReportedPosX;
			double d1 = event.getPosY() - lastReportedPosY;
			double d2 = posZ - lastReportedPosZ;
			double d3 = event.getRotationYaw() - lastReportedYaw;
			double d4 = event.getRotationPitch() - lastReportedPitch;
			++positionUpdateTicks;
			boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || positionUpdateTicks >= 20;
			boolean flag3 = d3 != 0.0D || d4 != 0.0D;

			if (isPassenger()) {
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
			autoJumpEnabled = Minecraft.getInstance().gameSettings.autoJump;
		}
	}

}
