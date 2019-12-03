package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.Deadmau5FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Deadmau5FeatureRenderer.class)
public class MixinLayerDeadmau5Head {

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public void render(MatrixStack matrixStack_1, VertexConsumerProvider vertexConsumerProvider_1, int int_1, AbstractClientPlayerEntity abstractClientPlayerEntity_1, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6) {
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
            VertexConsumer vertexConsumer_1 = vertexConsumerProvider_1.getBuffer(RenderLayer.getEntitySolid(abstractClientPlayerEntity_1.getSkinTexture()));
            int int_2 = LivingEntityRenderer.getOverlay(abstractClientPlayerEntity_1, 0.0F);

            for(int int_3 = 0; int_3 < 2; ++int_3) {
                float float_8 = MathHelper.lerp(float_3, abstractClientPlayerEntity_1.prevYaw, abstractClientPlayerEntity_1.yaw) - MathHelper.lerp(float_3, abstractClientPlayerEntity_1.prevBodyYaw, abstractClientPlayerEntity_1.bodyYaw);
                float float_9 = MathHelper.lerp(float_3, abstractClientPlayerEntity_1.prevPitch, abstractClientPlayerEntity_1.pitch);
                matrixStack_1.push();
                matrixStack_1.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(float_8));
                matrixStack_1.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(float_9));
                matrixStack_1.translate((double)(0.375F * (float)(int_3 * 2 - 1)), 0.0D, 0.0D);
                matrixStack_1.translate(0.0D, -0.375D, 0.0D);
                matrixStack_1.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-float_9));
                matrixStack_1.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-float_8));
                float float_10 = 1.3333334F;
                matrixStack_1.scale(1.3333334F, 1.3333334F, 1.3333334F);
                (((Deadmau5FeatureRenderer) (Object) this).getContextModel()).renderEars(matrixStack_1, vertexConsumer_1, int_1, int_2); // TODO: Maybe change beginning cast to PlayerEntityModel in 1.15?
                matrixStack_1.pop();
            }
        }
    }

}
