package me.deftware.mixin.imp;

public interface IMixinGameRenderer {
    float getFovMultiplier();

    void updateFovMultiplier(float newFov);
}
