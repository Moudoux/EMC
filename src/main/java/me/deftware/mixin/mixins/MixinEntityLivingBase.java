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
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(LivingEntity.class)
public class MixinEntityLivingBase implements IMixinEntityLivingBase {

    @Shadow
    @Final
    private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    @Shadow
    private int itemUseTimeLeft;

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public boolean hasStatusEffect(StatusEffect statusEffect_1) {
        if (!((LivingEntity) (Object) this instanceof ClientPlayerEntity)) {
            return activeStatusEffects.containsKey(statusEffect_1);
        }
        EventIsPotionActive event = new EventIsPotionActive(statusEffect_1.getTranslationKey(),
                activeStatusEffects.containsKey(statusEffect_1));
        event.broadcast();
        return event.isActive();
    }

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public float getJumpVelocity() {
        return (float) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "JUMP_HEIGHT", 0.42F);
    }

    @Override
    public int getActiveItemStackUseCount() {
        return itemUseTimeLeft;
    }

}
