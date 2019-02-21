package me.deftware.client.framework.wrappers.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;

public class IMovementInput {

    private static Input get() {
        return MinecraftClient.getInstance().player.input;
    }

    public static double getForward() {
        return IMovementInput.get().movementForward;
    }

    public static double getStrafe() {
        return IMovementInput.get().movementSideways;
    }

}
