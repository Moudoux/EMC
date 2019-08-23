package me.deftware.mixin.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.feature.Deadmau5FeatureRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Deadmau5FeatureRenderer.class)
public class MixinLayerDeadmau5Head {

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public void method_4181(AbstractClientPlayerEntity abstractClientPlayerEntity_1, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6, float float_7) {
        String usernames = (String) SettingsMap.getValue(SettingsMap.MapKeys.MISC, "DEADMAU_EARS", "");
        boolean flag = abstractClientPlayerEntity_1.getGameProfile().getName().equalsIgnoreCase(usernames);
        if (usernames.contains(",")) {
            for (String username : usernames.split(",")) {
                if (username.equalsIgnoreCase(abstractClientPlayerEntity_1.getGameProfile()
                        .getName())) {
                    flag = true;
                    break;
                }
            }
        }
        if (abstractClientPlayerEntity_1.hasSkinTexture() && !abstractClientPlayerEntity_1.isInvisible() && flag) {
            ((Deadmau5FeatureRenderer) (Object) this).bindTexture(abstractClientPlayerEntity_1.getSkinTexture());

            for (int int_1 = 0; int_1 < 2; ++int_1) {
                float float_8 = MathHelper.lerp(float_3, abstractClientPlayerEntity_1.prevYaw, abstractClientPlayerEntity_1.yaw) - MathHelper.lerp(float_3, abstractClientPlayerEntity_1.prevBodyYaw, abstractClientPlayerEntity_1.bodyYaw);
                float float_9 = MathHelper.lerp(float_3, abstractClientPlayerEntity_1.prevPitch, abstractClientPlayerEntity_1.pitch);
                RenderSystem.pushMatrix();
                RenderSystem.rotatef(float_8, 0.0F, 1.0F, 0.0F);
                RenderSystem.rotatef(float_9, 1.0F, 0.0F, 0.0F);
                RenderSystem.translatef(0.375F * (float) (int_1 * 2 - 1), 0.0F, 0.0F);
                RenderSystem.translatef(0.0F, -0.375F, 0.0F);
                RenderSystem.rotatef(-float_9, 1.0F, 0.0F, 0.0F);
                RenderSystem.rotatef(-float_8, 0.0F, 1.0F, 0.0F);
                float float_10 = 1.3333334F;
                RenderSystem.scalef(1.3333334F, 1.3333334F, 1.3333334F);
                (((Deadmau5FeatureRenderer) (Object) this).getModel()).renderEars(0.0625F);
                RenderSystem.popMatrix();
            }

        }
    }

}
