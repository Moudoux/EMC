package me.deftware.client.framework.render;

import me.deftware.client.framework.resource.ModResourceManager;
import me.deftware.client.framework.util.minecraft.MinecraftIdentifier;
import me.deftware.mixin.imp.Uniformable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderEffect;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Deftware
 */
public class Shader {

    public static final List<Shader> SHADERS = new ArrayList<>();

    private ShaderEffect shaderEffect;
    private Predicate<Object> targetPredicate = obj -> true;

    private Framebuffer framebuffer;
    private OutlineVertexConsumerProvider outlineVertexConsumerProvider;

    private boolean render = false, enabled = false;

    private final MinecraftIdentifier identifier;
    private final ModResourceManager resourceManager;

    public Shader(MinecraftIdentifier identifier, ModResourceManager resourceManager) {
        this.identifier = identifier;
        this.resourceManager = resourceManager;
    }

    public void init(MinecraftClient client, VertexConsumerProvider.Immediate entityVertexConsumers) {
        if (shaderEffect != null)
            shaderEffect.close();
        try {
            shaderEffect = new ShaderEffect(MinecraftClient.getInstance().getTextureManager(), resourceManager, MinecraftClient.getInstance().getFramebuffer(), identifier);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        shaderEffect.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
        framebuffer = shaderEffect.getSecondaryTarget("final");
        outlineVertexConsumerProvider = new OutlineVertexConsumerProvider(entityVertexConsumers);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setTargetPredicate(Predicate<Object> targetPredicate) {
        this.targetPredicate = targetPredicate;
    }

    public ShaderEffect getShaderEffect() {
        return shaderEffect;
    }

    public Uniformable getUniformable() {
        return (Uniformable) shaderEffect;
    }

    public Framebuffer getFramebuffer() {
        return framebuffer;
    }

    public Predicate<Object> getTargetPredicate() {
        return targetPredicate;
    }

    public OutlineVertexConsumerProvider getOutlineVertexConsumerProvider() {
        return outlineVertexConsumerProvider;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

}
