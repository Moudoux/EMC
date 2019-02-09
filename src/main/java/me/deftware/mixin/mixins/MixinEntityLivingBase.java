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
    private Map<StatusEffect, StatusEffectInstance> activePotionEffects;

    @Shadow
    private int field_6222;

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public boolean hasPotionEffect(StatusEffect statusEffect_1) {
        if (!((LivingEntity) (Object) this instanceof ClientPlayerEntity)) {
            return activePotionEffects.containsKey(statusEffect_1);
        }
        EventIsPotionActive event = new EventIsPotionActive(statusEffect_1.getTranslationKey(),
                activePotionEffects.containsKey(statusEffect_1)).send();
        return event.isActive();
    }

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public float method_6106() {
        return (float) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "JUMP_HEIGHT", 0.42F);
    }

    @Override
    public int getActiveItemStackUseCount() {
        return field_6222;
    }

}
