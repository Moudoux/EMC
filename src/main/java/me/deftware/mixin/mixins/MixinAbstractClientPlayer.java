package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventFovModifier;
import me.deftware.client.framework.event.events.EventSpectator;
import me.deftware.mixin.imp.IMixinAbstractClientPlayer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class MixinAbstractClientPlayer implements IMixinAbstractClientPlayer {

    @Shadow
    private PlayerListEntry cachedScoreboardEntry;

    @Inject(method = "isSpectator", at = @At(value = "TAIL"), cancellable = true)
    private void onIsSpectator(CallbackInfoReturnable<Boolean> cir) {
        EventSpectator event = new EventSpectator(cir.getReturnValue());
        cir.setReturnValue(event.isSpectator());
    }

    @ModifyVariable(method = "getSpeed", ordinal = 0, at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getAttributeInstance(Lnet/minecraft/entity/attribute/EntityAttribute;)Lnet/minecraft/entity/attribute/EntityAttributeInstance;"))
    private float onGetSpeed(float f) {
        EventFovModifier event = new EventFovModifier(f);
        event.broadcast();
        return event.getFov();
    }

    @Override
    public PlayerListEntry getPlayerNetworkInfo() {
        return cachedScoreboardEntry;
    }

}
