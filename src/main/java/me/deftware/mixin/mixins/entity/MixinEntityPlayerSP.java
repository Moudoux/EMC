package me.deftware.mixin.mixins.entity;

import me.deftware.client.framework.chat.builder.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.event.events.*;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.render.camera.entity.CameraEntityMan;
import me.deftware.mixin.imp.IMixinEntityPlayerSP;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinEntityPlayerSP extends MixinEntity implements IMixinEntityPlayerSP {

    @Shadow
    @Final
    protected MinecraftClient client;

    @Shadow
    @Final
    public ClientPlayNetworkHandler networkHandler;

    @Shadow
    private float mountJumpStrength;

    @Shadow
    public abstract boolean isUsingItem();

    @Shadow
    public abstract float getYaw(float tickDelta);

    @Inject(method = "closeHandledScreen", at = @At("HEAD"))
    private void onCloseHandledScreen(CallbackInfo ci) {
        new EventGuiContainerClose().broadcast();
    }
    
    @Inject(at = @At("HEAD"), cancellable = true, method = "isCamera")
    public void isCamera(CallbackInfoReturnable<Boolean> info) {
        if (CameraEntityMan.isActive()) {
            info.setReturnValue(true);
            info.cancel();
        }
    }

    @Unique
    private final EventSlowdown eventSlowdown = new EventSlowdown();

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.isUsingItem()Z", ordinal = 0))
    private boolean itemUseSlowdownEvent(ClientPlayerEntity self) {
        eventSlowdown.create(EventSlowdown.SlowdownType.Item_Use, 1);
        eventSlowdown.broadcast();
        if (eventSlowdown.isCanceled()) {
            return false;
        }
        return isUsingItem();
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "net/minecraft/entity/player/HungerManager.getFoodLevel()I"))
    private int hungerSlowdownEvent(HungerManager self) {
        eventSlowdown.create(EventSlowdown.SlowdownType.Hunger, 1);
        eventSlowdown.broadcast();
        if (eventSlowdown.isCanceled()) {
            return 7;
        }
        return self.getFoodLevel();
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"))
    private boolean onBlindnessSlowdown(ClientPlayerEntity self, StatusEffect effect) {
        eventSlowdown.create(EventSlowdown.SlowdownType.Blindness, 1);
        eventSlowdown.broadcast();
        if (eventSlowdown.isCanceled()) {
            return false;
        }
        return self.hasStatusEffect(effect);
    }

    @Unique
    private final EventUpdate eventUpdate = new EventUpdate();

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        if (MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().player != null) {
            eventUpdate.create(((ClientPlayerEntity) (Object) this).getX(), ((ClientPlayerEntity) (Object) this).getY(), ((ClientPlayerEntity) (Object) this).getZ(), getYaw(), getPitch(), onGround);
            eventUpdate.broadcast();
            if (eventUpdate.isCanceled()) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void sendChatMessage(String message, CallbackInfo ci) {
        sendChatMessageWithSender(message, ClientPlayerEntity.class);
        ci.cancel();
    }

    @Unique
    public void sendChatMessageWithSender(String message, Class<?> sender) {
        String trigger = CommandRegister.getCommandTrigger();
        EventChatSend event = new EventChatSend(message, sender).broadcast();
        if (!event.isCanceled()) {
            if (event.isDispatch() || !message.startsWith(trigger)) {
                networkHandler.sendPacket(new ChatMessageC2SPacket(event.getMessage()));
            } else {
                try {
                    CommandRegister.getDispatcher().execute(message.substring(CommandRegister.getCommandTrigger().length()), MinecraftClient.getInstance().player.getCommandSource());
                } catch (Exception ex) {
                    Bootstrap.logger.error("Failed to execute command", ex);
                    new ChatBuilder().withText(ex.getMessage()).withColor(ChatColors.RED).build().print();
                }
            }
        }
    }

    @Override
    public void setHorseJumpPower(float height) {
        mountJumpStrength = height; // TODO: Verify
    }

    @Unique
    private final EventPlayerWalking eventPlayerWalking = new EventPlayerWalking();

    @Inject(method = "sendMovementPackets", at = @At(value = "HEAD"), cancellable = true)
    private void onSendMovementPackets(CallbackInfo ci) {
        ClientPlayerEntity entity = (ClientPlayerEntity) (Object) this;
        eventPlayerWalking.create(entity.getX(), entity.getY(), entity.getZ(), getYaw(), getPitch(), onGround);
        eventPlayerWalking.broadcast();
        if (eventPlayerWalking.isCanceled()) {
            ci.cancel();
        }
    }

    @Unique
    private final EventPlayerWalking.PostEvent postEvent = new EventPlayerWalking.PostEvent();

    @Inject(method = "sendMovementPackets", at = @At(value = "TAIL"), cancellable = true)
    private void onSendMovementPacketsTail(CallbackInfo ci) {
        ClientPlayerEntity entity = (ClientPlayerEntity) (Object) this;
        postEvent.create(entity.getX(), entity.getY(), entity.getZ(), getYaw(), getPitch(), onGround);
        postEvent.broadcast();
        if (postEvent.isCanceled()) {
            ci.cancel();
        }
    }

}
