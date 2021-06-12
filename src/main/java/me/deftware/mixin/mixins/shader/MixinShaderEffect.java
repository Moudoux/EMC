package me.deftware.mixin.mixins.shader;

import me.deftware.mixin.imp.Uniformable;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.PostProcessShader;
import net.minecraft.client.gl.ShaderEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(ShaderEffect.class)
public class MixinShaderEffect implements Uniformable {

    @Shadow
    @Final
    private List<PostProcessShader> passes;

    @Unique
    private final Map<String, Runnable> modifications = new ConcurrentHashMap<>();

    @Unique
    @Override
    public void registerUniformf(String name, float[] values) {
        Runnable shaderConsumer = () -> {
            for (PostProcessShader shader : passes) {
                GlUniform uniform = shader.getProgram().getUniformByName(name);
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
        };
        modifications.put(name, shaderConsumer);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(float tickDelta, CallbackInfo ci) {
        modifications.values().forEach(Runnable::run);
        modifications.clear();
    }

}
