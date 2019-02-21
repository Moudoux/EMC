package me.deftware.mixin.mixins;

import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.event.events.*;
import me.deftware.client.framework.utils.ChatColor;
import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.client.framework.wrappers.IChat;
import me.deftware.mixin.imp.IMixinEntityPlayerSP;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;
import net.minecraft.server.network.packet.ClientCommandC2SPacket;
import net.minecraft.server.network.packet.PlayerMoveServerMessage;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinEntityPlayerSP extends MixinEntity implements IMixinEntityPlayerSP {

    @Shadow
    @Final
    public ClientPlayNetworkHandler networkHandler;
    @Shadow
    private boolean field_3920;
    @Shadow
    private boolean field_3919;
    @Shadow
    private boolean field_3936;
    @Shadow
    private boolean field_3927;
    @Shadow
    private float field_3941;
    @Shadow
    private float field_3925;
    @Shadow
    private double field_3926;
    @Shadow
    private double field_3940;
    @Shadow
    private double field_3924;
    @Shadow
    private int field_3923;
    @Shadow
    private float field_3922;

    @Shadow
    protected abstract boolean method_3134();

    @Shadow
    public abstract boolean isUsingItem();

    @Redirect(method = "updateMovement", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.isUsingItem()Z", ordinal = 0))
    private boolean itemUseSlowdownEvent(ClientPlayerEntity self) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Item_Use).send();
        if (event.isCanceled()) {
            return false;
        }
        return isUsingItem();
    }

    @Redirect(method = "updateMovement", at = @At(value = "INVOKE", target = "net/minecraft/entity/player/HungerManager.getFoodLevel()I"))
    private int hungerSlowdownEvent(HungerManager self) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Hunger).send();
        if (event.isCanceled()) {
            return 7;
        }
        return self.getFoodLevel();
    }

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void update(CallbackInfo ci) {
        EventUpdate event = new EventUpdate(x, y, z, yaw, pitch, onGround).send();
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
                    networkHandler.sendPacket(new ChatMessageC2SPacket(message.substring((trigger + "say ").length())));
                    ci.cancel();
                    return;
                }
                CommandRegister.getDispatcher().execute(message.substring(CommandRegister.getCommandTrigger().length()), MinecraftClient.getInstance().player.getCommandSource());
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
            new EventIRCMessage(message).send();
            ci.cancel();
            return;
        }
        EventChatSend event = new EventChatSend(message).send();
        if (event.isCanceled()) {
            ci.cancel();
        } else if (!event.getMessage().equals(message)) {
            networkHandler.sendPacket(new ChatMessageC2SPacket(event.getMessage()));
            ci.cancel();
        }
    }


    @Override
    public void setHorseJumpPower(float height) {
        field_3922 = height;
    }

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    private void method_3136() {
        EventPlayerWalking event = new EventPlayerWalking(x, y, z, yaw, pitch, onGround)
                .send();
        if (event.isCanceled()) {
            return;
        }

        boolean boolean_1 = this.isSprinting();
        if (boolean_1 != this.field_3919) {
            if (boolean_1) {
                this.networkHandler.sendPacket(new ClientCommandC2SPacket((ClientPlayerEntity) (Object) this, ClientCommandC2SPacket.Mode.START_SPRINTING));
            } else {
                this.networkHandler.sendPacket(new ClientCommandC2SPacket((ClientPlayerEntity) (Object) this, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
            }

            this.field_3919 = boolean_1;
        }

        boolean boolean_2 = this.isSneaking();
        if (boolean_2 != this.field_3936) {
            if (boolean_2) {
                this.networkHandler.sendPacket(new ClientCommandC2SPacket((ClientPlayerEntity) (Object) this, ClientCommandC2SPacket.Mode.START_SNEAKING));
            } else {
                this.networkHandler.sendPacket(new ClientCommandC2SPacket((ClientPlayerEntity) (Object) this, ClientCommandC2SPacket.Mode.STOP_SNEAKING));
            }

            this.field_3936 = boolean_2;
        }

        if (method_3134()) {
            BoundingBox axisalignedbb = getBoundingBox();
            double d0 = x - field_3926;
            double d1 = event.getPosY() - field_3940;
            double d2 = z - field_3924;
            double d3 = event.getRotationYaw() - field_3941;
            double d4 = event.getRotationPitch() - field_3925;
            ++field_3923;
            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || field_3923 >= 20;
            boolean flag3 = d3 != 0.0D || d4 != 0.0D;

            if (hasVehicle()) {
                Vec3d vec3d_1 = ((ClientPlayerEntity) (Object) this).getVelocity();
                this.networkHandler.sendPacket(new PlayerMoveServerMessage.Both(vec3d_1.x, -999.0D, vec3d_1.z,
                        event.getRotationYaw(), event.getRotationPitch(), event.isOnGround()));
                flag2 = false;
            } else if ((flag2 && flag3)) {
                this.networkHandler.sendPacket(new PlayerMoveServerMessage.Both(x, event.getPosY(), z,
                        event.getRotationYaw(), event.getRotationPitch(), event.isOnGround()));
            } else if (flag2) {
                this.networkHandler.sendPacket(new PlayerMoveServerMessage.PositionOnly(x, event.getPosY(), z, event.isOnGround()));
            } else if (flag3) {
                this.networkHandler.sendPacket(new PlayerMoveServerMessage.LookOnly(event.getRotationYaw(), event.getRotationPitch(),
                        event.isOnGround()));
            } else if (field_3920 != onGround) {
                this.networkHandler.sendPacket(new PlayerMoveServerMessage(event.isOnGround()));
            }

            if (flag2) {
                this.field_3926 = this.x;
                this.field_3940 = axisalignedbb.minY;
                this.field_3924 = this.z;
                this.field_3923 = 0;
            }

            if (flag3) {
                this.field_3941 = this.yaw;
                this.field_3925 = this.pitch;
            }

            this.field_3920 = this.onGround;
            this.field_3927 = MinecraftClient.getInstance().options.autoJump;
        }

    }

}
