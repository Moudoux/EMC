package me.deftware.client.framework.wrappers;

import me.deftware.mixin.imp.IMixinKeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class IKeybindWrapper {

    public static boolean isPressed(IKeybind bind) {
        return bind.bind.isPressed();
    }

    public static void setPressed(IKeybind bind, boolean state) {
        ((IMixinKeyBinding) bind.bind).setPressed(state);
    }

    public static boolean isKeyDown(IKeybind bind) {
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().window.getHandle(), ((IMixinKeyBinding) bind.bind).getInput().getKeyCode());
    }

    public static String getKeyName(int key) {
        if (key == 344) {
            return "RShift";
        }
        return String.valueOf((char) key);
    }

    public static boolean isKeyDown(int key) {
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().window.getHandle(), key);
    }

    public static enum IKeybind {

        SNEAK(MinecraftClient.getInstance().options.keySneak),
        USEITEM(MinecraftClient.getInstance().options.keyUse),
        JUMP(MinecraftClient.getInstance().options.keyJump),
        SPRINT(MinecraftClient.getInstance().options.keySprint),
        FORWARD(MinecraftClient.getInstance().options.keyForward),
        BACK(MinecraftClient.getInstance().options.keyBack),
        LEFT(MinecraftClient.getInstance().options.keyLeft),
        RIGHT(MinecraftClient.getInstance().options.keyRight),
        ATTACK(MinecraftClient.getInstance().options.keyAttack);

        KeyBinding bind;

        IKeybind(KeyBinding bind) {
            this.bind = bind;
        }

    }

}
