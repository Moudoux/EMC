package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.event.events.EventRenderPlayerModel;
import me.deftware.client.framework.global.GameKeys;
import me.deftware.client.framework.global.GameMap;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinRenderLivingBase<T extends LivingEntity> {

    @Unique
    private final EventRenderPlayerModel eventRenderPlayerModel = new EventRenderPlayerModel();

    @Inject(method = "isVisible", at = @At("HEAD"), cancellable = true)
    private void isVisible(T entity, CallbackInfoReturnable<Boolean> ci) {
        eventRenderPlayerModel.create(entity);
        eventRenderPlayerModel.broadcast();
        if (eventRenderPlayerModel.isShouldRender()) {
            ci.setReturnValue(true);
        }
    }

    @Inject(method = "setupTransforms", at = @At("RETURN"))
    protected void setupTransforms(T entity, MatrixStack matrices, float x, float y, float z, CallbackInfo ci) {
        if (!(entity instanceof PlayerEntity)) {
            return;
        }
        String s = ((PlayerEntity) entity).getGameProfile().getName(); //TextFormatting.getTextWithoutFormattingCodes(((EntityPlayer) entityLivingBaseIn).getGameProfile().getName());
        String names = GameMap.INSTANCE.get(GameKeys.FLIP_USERNAMES, "");
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
                matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
            }
        }
    }

}
