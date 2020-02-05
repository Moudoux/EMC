package me.deftware.mixin.imp;

import me.deftware.client.framework.wrappers.IResourceLocation;

public interface IMixinEntityRenderer {

    void loadCustomShader(IResourceLocation location);

    float getFovMultiplier();

    void updateFovMultiplier(float newFov);

}
