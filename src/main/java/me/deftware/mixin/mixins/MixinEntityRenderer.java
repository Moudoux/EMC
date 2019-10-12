package me.deftware.mixin.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.event.events.EventHurtcam;
import me.deftware.client.framework.event.events.EventRender2D;
import me.deftware.client.framework.event.events.EventRender3D;
import me.deftware.client.framework.event.events.EventWeather;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.mixin.imp.IMixinEntityRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileUtil;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

@Mixin(GameRenderer.class)
public abstract class MixinEntityRenderer implements IMixinEntityRenderer {

    @Shadow
    private boolean renderHand;
    private float partialTicks = 0;

    @Shadow
    public abstract void loadShader(Identifier p_loadShader_1_);

    /* TODO: FIX
    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "com/mojang/blaze3d/systems/RenderSystem.enableDepthTest()V"))
    private void renderWorld(CallbackInfo ci) {
        if (!((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "WORLD_DEPTH", true))) {
            RenderSystem.disableDepthTest();
        }
    }*/

    @Redirect(method = "renderWorld", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = GETFIELD))
    private boolean updateCameraAndRender_renderHand(GameRenderer self) {
        new EventRender3D(partialTicks).broadcast();
        return renderHand;
    }

    @Inject(method = "renderWorld", at = @At("HEAD"))
    private void updateCameraAndRender(float partialTicks, long finishTimeNano, MatrixStack stack, CallbackInfo ci) {
        this.partialTicks = partialTicks;
    }

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void hurtCameraEffect(MatrixStack stack, float partialTicks, CallbackInfo ci) {
        EventHurtcam event = new EventHurtcam();
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/hud/InGameHud.render(F)V"))
    private void onRender2D(CallbackInfo cb) {
        ChatProcessor.sendMessages();
        new EventRender2D(0f).broadcast();
    }

    @Override
    public void loadCustomShader(IResourceLocation location) {
        loadShader(location);
    }

    @Shadow
    @Final
    public MinecraftClient client;

    @Overwrite
    public void updateTargetedEntity(float float_1) {
        Entity entity_1 = this.client.getCameraEntity();
        if (entity_1 != null) {
            if (this.client.world != null) {
                // EMC
                boolean bypassReach = (boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "BYPASS_REACH_LIMIT", false);
                //
                this.client.getProfiler().push("pick");
                this.client.targetedEntity = null;
                double double_1 = (double)this.client.interactionManager.getReachDistance();
                this.client.hitResult = entity_1.rayTrace(double_1, float_1, false);
                Vec3d vec3d_1 = entity_1.getCameraPosVec(float_1);
                boolean boolean_1 = false;
                double double_2 = double_1;
                if (this.client.interactionManager.hasExtendedReach()) {
                    // EMC
                    if (!bypassReach) {
                        double_2 = 6.0D;
                        double_1 = double_2;
                    }
                } else {
                    if (double_1 > 3.0D) {
                        boolean_1 = true;
                    }
                }

                double_2 *= double_2;
                if (this.client.hitResult != null) {
                    double_2 = this.client.hitResult.getPos().squaredDistanceTo(vec3d_1);
                }

                Vec3d vec3d_2 = entity_1.getRotationVec(1.0F);
                Vec3d vec3d_3 = vec3d_1.add(vec3d_2.x * double_1, vec3d_2.y * double_1, vec3d_2.z * double_1);
                float float_2 = 1.0F;
                Box boundingBox_1 = entity_1.getBoundingBox().stretch(vec3d_2.multiply(double_1)).expand(1.0D, 1.0D, 1.0D);
                EntityHitResult entityHitResult_1 = ProjectileUtil.rayTrace(entity_1, vec3d_1, vec3d_3, boundingBox_1, (entity_1x) -> {
                    return !entity_1x.isSpectator() && entity_1x.collides();
                }, bypassReach ? 0D : double_2);
                if (entityHitResult_1 != null) {
                    Entity entity_2 = entityHitResult_1.getEntity();
                    Vec3d vec3d_4 = entityHitResult_1.getPos();
                    double double_3 = bypassReach ? 2D : vec3d_1.squaredDistanceTo(vec3d_4);
                    if (boolean_1 && double_3 > 9.0D) {
                        this.client.hitResult = BlockHitResult.createMissed(vec3d_4, Direction.getFacing(vec3d_2.x, vec3d_2.y, vec3d_2.z), new BlockPos(vec3d_4));
                    } else if (double_3 < double_2 || this.client.hitResult == null) {
                        this.client.hitResult = entityHitResult_1;
                        if (entity_2 instanceof LivingEntity || entity_2 instanceof ItemFrameEntity) {
                            this.client.targetedEntity = entity_2;
                        }
                    }
                }

                this.client.getProfiler().pop();
            }
        }
    }

}
