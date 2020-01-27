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
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
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
    private float field_3922;

    @Shadow
    public abstract boolean isUsingItem();

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.isUsingItem()Z", ordinal = 0))
    private boolean itemUseSlowdownEvent(ClientPlayerEntity self) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Item_Use);
        event.broadcast();
        if (event.isCanceled()) {
            return false;
        }
        return isUsingItem();
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "net/minecraft/entity/player/HungerManager.getFoodLevel()I"))
    private int hungerSlowdownEvent(HungerManager self) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Hunger);
        event.broadcast();
        if (event.isCanceled()) {
            return 7;
        }
        return self.getFoodLevel();
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    private boolean onBlindnessSlowdown(ClientPlayerEntity self, StatusEffect effect) {
        EventSlowdown event = new EventSlowdown(EventSlowdown.SlowdownType.Blindness);
        event.broadcast();
        if (event.isCanceled()) {
            return false;
        }
        return self.hasStatusEffect(effect);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        EventUpdate event = new EventUpdate(((ClientPlayerEntity) (Object) this).getX(), ((ClientPlayerEntity) (Object) this).getY(), ((ClientPlayerEntity) (Object) this).getZ(), yaw, pitch, onGround);
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
            new EventIRCMessage(message).broadcast();
            ci.cancel();
            return;
        }
        EventChatSend event = new EventChatSend(message);
        event.broadcast();
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


    @Inject(method = "sendMovementPackets", at = @At(value = "HEAD"), cancellable = true)
    private void onSendMovementPackets(CallbackInfo ci) {
        ClientPlayerEntity entity = (ClientPlayerEntity) (Object) this;
        EventPlayerWalking event = new EventPlayerWalking(entity.getX(), entity.getY(), entity.getZ(), yaw, pitch, onGround);
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
