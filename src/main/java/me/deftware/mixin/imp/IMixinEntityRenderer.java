package me.deftware.mixin.imp;

import me.deftware.client.framework.render.Shader;
import me.deftware.client.framework.util.minecraft.MinecraftIdentifier;

public interface IMixinEntityRenderer {

    void loadCustomShader(MinecraftIdentifier location);

    void loadShader(Shader shader);

    float getFovMultiplier();

    void updateFovMultiplier(float newFov);

}
