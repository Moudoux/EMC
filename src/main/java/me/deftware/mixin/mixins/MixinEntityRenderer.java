package me.deftware.mixin.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.event.events.EventHurtcam;
import me.deftware.client.framework.event.events.EventRender2D;
import me.deftware.client.framework.event.events.EventRender3D;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.mixin.imp.IMixinEntityRenderer;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ProjectileUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(GameRenderer.class)
public abstract class MixinEntityRenderer implements IMixinEntityRenderer {

    private float partialTicks = 0;

    @Shadow
    private float movementFovMultiplier;

    @Shadow
    private float lastMovementFovMultiplier;

    @Shadow
    protected abstract void loadShader(Identifier p_loadShader_1_);

    @Inject(method = "renderHand", at = @At("HEAD"))
    private void renderHand(MatrixStack matrixStack_1, Camera camera_1, float float_1, CallbackInfo ci) {
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(matrixStack_1.peek().getModel());
        new EventRender3D(partialTicks).broadcast();
        RenderSystem.popMatrix();
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

    private boolean bypassReach;

    @Inject(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V"))
    private void onBypassReachInitialize(float tickDelta, CallbackInfo ci) {
        bypassReach = (boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "BYPASS_REACH_LIMIT", true);
    }

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ProjectileUtil;rayTrace(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"))
    private EntityHitResult onRayTraceDistance(Entity entity, Vec3d vec3d, Vec3d vec3d2, Box box, Predicate<Entity> predicate, double d) {
        return ProjectileUtil.rayTrace(entity, vec3d, vec3d2, box, predicate, bypassReach ? 0d : d);
    }

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;squaredDistanceTo(Lnet/minecraft/util/math/Vec3d;)D", ordinal = 1))
    private double onDistance(Vec3d self, Vec3d vec3d) {
        return bypassReach ? 2D : self.squaredDistanceTo(vec3d);
    }

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;hasExtendedReach()Z"))
    private boolean onTest(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        return !bypassReach && clientPlayerInteractionManager.hasExtendedReach();
    }

    @Override
    public float getFovMultiplier() {
        return movementFovMultiplier;
    }

    @Override
    public void updateFovMultiplier(float newFov) {
        lastMovementFovMultiplier = newFov;
        movementFovMultiplier = newFov;
    }

}
