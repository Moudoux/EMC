package me.deftware.client.framework.render.shader;

import me.deftware.client.framework.resource.ModResourceManager;
import me.deftware.client.framework.util.minecraft.MinecraftIdentifier;
import me.deftware.mixin.imp.Uniformable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.PostProcessShader;
import net.minecraft.client.gl.ShaderEffect;

import java.util.List;

/**
 * @author Deftware
 */
public class Shader {

    private ShaderEffect shaderEffect;
    private Framebuffer framebuffer;

    private final MinecraftIdentifier identifier;
    private final ModResourceManager resourceManager;

    public Shader(MinecraftIdentifier identifier, ModResourceManager resourceManager) {
        this.identifier = identifier;
        this.resourceManager = resourceManager;
    }

    public void init() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (shaderEffect == null) {
            try {
                shaderEffect = new ShaderEffect(MinecraftClient.getInstance().getTextureManager(), resourceManager, MinecraftClient.getInstance().getFramebuffer(), identifier);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            shaderEffect.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            framebuffer = new Framebuffer(shaderEffect.getSecondaryTarget("final"));
        }
    }

    public void close() {
        shaderEffect.close();
    }

    public void resize(int width, int height) {
        shaderEffect.setupDimensions(width, height);
    }

    public void setUniform(String name, float... values) {
        List<PostProcessShader> passes = getUniformable().getPostShaders();
        for (PostProcessShader pass : passes) {
            GlUniform uniform = pass.getProgram().getUniformByName(name);
            if (uniform != null) {
                if (values.length == 4) {
                    uniform.set(values[0], values[1], values[2], values[3]);
                } else if (values.length == 3) {
                    uniform.set(values[0], values[1], values[2]);
                } else if (values.length == 2) {
                    uniform.set(values[0], values[1]);
                } else if (values.length == 1) {
                    uniform.set(values[0]);
                }
            }
        }
    }

    public boolean isLoaded() {
        return shaderEffect != null && framebuffer != null;
    }

    public void render(float partialTicks) {
        shaderEffect.render(partialTicks);
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

}
