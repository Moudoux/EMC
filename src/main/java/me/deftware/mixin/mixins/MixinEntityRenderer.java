package me.deftware.mixin.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.chat.hud.ChatHud;
import me.deftware.client.framework.event.events.EventHurtcam;
import me.deftware.client.framework.event.events.EventRender2D;
import me.deftware.client.framework.event.events.EventRender3D;
import me.deftware.client.framework.event.events.EventRender3DNoBobbing;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.mixin.imp.IMixinEntityRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(GameRenderer.class)
public abstract class MixinEntityRenderer implements IMixinEntityRenderer {

    @Shadow
    private float movementFovMultiplier;

    @Shadow
    private float lastMovementFovMultiplier;

    @Shadow
    protected abstract void loadShader(Identifier p_loadShader_1_);

    @Shadow
    public abstract Matrix4f getBasicProjectionMatrix(Camera camera, float f, boolean bl);

    @Shadow
    @Final
    private Camera camera;

    @Inject(method = "renderHand", at = @At("HEAD"))
    private void renderHand(MatrixStack matrixStack_1, Camera camera_1, float partialTicks, CallbackInfo ci) {
        // Normal 3d event
        loadPushPop(() -> new EventRender3D(partialTicks).broadcast(), matrixStack_1);
        // Camera model stack without bobbing applied
        MatrixStack projectionMatrix = new MatrixStack();
        projectionMatrix.peek().getModel().multiply(this.getBasicProjectionMatrix(camera, partialTicks, true));
        MinecraftClient.getInstance().gameRenderer.loadProjectionMatrix(projectionMatrix.peek().getModel());
        // Camera transformation stack
        MatrixStack cameraMatrix = new MatrixStack();
        cameraMatrix.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));
        cameraMatrix.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(camera.getYaw() + 180f));
        loadPushPop(() -> new EventRender3DNoBobbing(partialTicks).broadcast(), cameraMatrix);
        // Reset projection
        MinecraftClient.getInstance().gameRenderer.loadProjectionMatrix(matrixStack_1.peek().getModel());
    }

    @Unique
    private void loadPushPop(Runnable action, MatrixStack stack) {
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        RenderSystem.multMatrix(stack.peek().getModel());
        action.run();
        RenderSystem.popMatrix();
    }

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void hurtCameraEffect(MatrixStack stack, float partialTicks, CallbackInfo ci) {
        EventHurtcam event = new EventHurtcam();
        event.broadcast();
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V"))
    private void onRender2D(CallbackInfo cb) {
        Runnable operation = ChatHud.getChatMessageQueue().poll();
        if (operation != null) {
            operation.run();
        }
        new EventRender2D(0f).broadcast();
    }

    @Override
    public void loadCustomShader(IResourceLocation location) {
        loadShader(location);
    }

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileUtil;rayTrace(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"))
    private EntityHitResult onRayTraceDistance(Entity entity, Vec3d vec3d, Vec3d vec3d2, Box box, Predicate<Entity> predicate, double d) {
        return ProjectileUtil.rayTrace(entity, vec3d, vec3d2, box, predicate, (boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "BYPASS_REACH_LIMIT", false) ? 0d : d);
    }

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;squaredDistanceTo(Lnet/minecraft/util/math/Vec3d;)D", ordinal = 1))
    private double onDistance(Vec3d self, Vec3d vec3d) {
        return (boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "BYPASS_REACH_LIMIT", false) ? 2D : self.squaredDistanceTo(vec3d);
    }

    @Redirect(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;hasExtendedReach()Z"))
    private boolean onTest(ClientPlayerInteractionManager clientPlayerInteractionManager) {
        return !(boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "BYPASS_REACH_LIMIT", false) && clientPlayerInteractionManager.hasExtendedReach();
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
