package me.deftware.mixin.imp;

import me.deftware.client.framework.util.minecraft.MinecraftIdentifier;

public interface IMixinEntityRenderer {

    void loadCustomShader(MinecraftIdentifier location);

    float getFovMultiplier();

    void updateFovMultiplier(float newFov);

}
