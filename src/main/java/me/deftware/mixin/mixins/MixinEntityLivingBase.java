package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventIsPotionActive;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.imp.IMixinEntityLivingBase;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public class MixinEntityLivingBase implements IMixinEntityLivingBase {

    @Shadow
    @Final
    private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    @Shadow
    protected int itemUseTimeLeft;


    @SuppressWarnings("ConstantConditions")
    @Inject(method = "hasStatusEffect", at = @At(value = "TAIL"), cancellable = true)
    private void onHasStatusEffect(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
        if (!((LivingEntity) (Object) this instanceof ClientPlayerEntity)) {
            return;
        }

        EventIsPotionActive event = new EventIsPotionActive(effect.getTranslationKey(), activeStatusEffects.containsKey(effect));
        event.broadcast();
        cir.setReturnValue(event.isActive());
    }


    @Inject(method = "getJumpVelocity", at = @At(value = "TAIL"), cancellable = true)
    private void onGetJumpVelocity(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "JUMP_HEIGHT", cir.getReturnValue()));
    }

    @Override
    public int getActiveItemStackUseCount() {
        return itemUseTimeLeft;
    }

}
