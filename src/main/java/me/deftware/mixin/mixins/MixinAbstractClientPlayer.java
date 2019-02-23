package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventFovModifier;
import me.deftware.client.framework.event.events.EventSpectator;
import me.deftware.mixin.imp.IMixinAbstractClientPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ScoreboardEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class MixinAbstractClientPlayer implements IMixinAbstractClientPlayer {

    @Shadow
    private ScoreboardEntry field_3901;

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public boolean isSpectator() {
        ScoreboardEntry scoreboardEntry_1 = MinecraftClient.getInstance().getNetworkHandler().method_2871(((PlayerEntity) (Object) this).getGameProfile().getId());
        EventSpectator event = new EventSpectator(scoreboardEntry_1 != null && scoreboardEntry_1.getGameMode() == GameMode.SPECTATOR);
        return event.isSpectator();
    }

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public float method_3118() {
        float float_1 = 1.0F;
        if (((PlayerEntity) (Object) this).abilities.flying) {
            float_1 *= 1.1F;
        }

        EventFovModifier event = new EventFovModifier(float_1);
        event.broadcast();
        float_1 = event.getFov();

        EntityAttributeInstance entityAttributeInstance_1 = ((LivingEntity) (Object) this).getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        float_1 = (float) ((double) float_1 * ((entityAttributeInstance_1.getValue() / (double) ((PlayerEntity) (Object) this).abilities.getWalkSpeed() + 1.0D) / 2.0D));
        if (((PlayerEntity) (Object) this).abilities.getWalkSpeed() == 0.0F || Float.isNaN(float_1) || Float.isInfinite(float_1)) {
            float_1 = 1.0F;
        }

        if (((LivingEntity) (Object) this).isUsingItem() && ((LivingEntity) (Object) this).getActiveItem().getItem() == Items.BOW) {
            int int_1 = ((LivingEntity) (Object) this).method_6048();
            float float_2 = (float) int_1 / 20.0F;
            if (float_2 > 1.0F) {
                float_2 = 1.0F;
            } else {
                float_2 *= float_2;
            }

            float_1 *= 1.0F - float_2 * 0.15F;
        }

        return float_1;
    }

    @Override
    public ScoreboardEntry getPlayerNetworkInfo() {
        return field_3901;
    }

}
