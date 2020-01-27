package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventRenderPlayerModel;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinRenderLivingBase<T extends LivingEntity> {

    @Inject(method = "method_4056", at = @At("HEAD"), cancellable = true)
    private void isVisible(T p_193115_1_, CallbackInfoReturnable<Boolean> ci) {
        EventRenderPlayerModel event = new EventRenderPlayerModel(p_193115_1_);
        event.broadcast();
        if (event.isShouldRender()) {
            ci.setReturnValue(true);
        }
    }

    @Inject(method = "setupTransforms", at = @At("RETURN"))
    protected void setupTransforms(T livingEntity_1, MatrixStack matrixStack_1, float x, float y, float z, CallbackInfo ci) {
        if (!(livingEntity_1 instanceof PlayerEntity)) {
            return;
        }
        String s = ((PlayerEntity) livingEntity_1).getGameProfile().getName(); //TextFormatting.getTextWithoutFormattingCodes(((EntityPlayer) entityLivingBaseIn).getGameProfile().getName());
        String names = (String) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "FLIP_USERNAMES", "");
        if (s != null && !names.equals("")) {
            boolean flip = false;
            if (names.contains(",")) {
                for (String name : names.split(",")) {
                    if (name.equals(s)) {
                        flip = true;
                        break;
                    }
                }
            } else {
                flip = names.equals(s);
            }
            if (flip) {
                matrixStack_1.translate(0.0D, livingEntity_1.getHeight() + 0.1F, 0.0D);
                matrixStack_1.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
            }
        }
    }

}
