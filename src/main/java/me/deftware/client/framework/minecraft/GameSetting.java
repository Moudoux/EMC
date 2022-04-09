package me.deftware.client.framework.minecraft;

import net.minecraft.client.MinecraftClient;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class GameSetting<T> {

    public static GameSetting<Integer> VIEW_DISTANCE = getSimpleOption(() -> MinecraftClient.getInstance().options.viewDistance,
            value -> MinecraftClient.getInstance().options.viewDistance = value);
    public static GameSetting<Double> GAMMA = getSimpleOption(() -> MinecraftClient.getInstance().options.gamma,
            value -> MinecraftClient.getInstance().options.gamma = value);
    public static GameSetting<Integer> MAX_FPS = getSimpleOption(() -> MinecraftClient.getInstance().options.maxFps,
            value -> {
                MinecraftClient.getInstance().options.maxFps = value;
                MinecraftClient.getInstance().getWindow().setFramerateLimit(value);
            });
    public static GameSetting<Boolean> DEBUG_INFO = getSimpleOption(() -> MinecraftClient.getInstance().options.debugEnabled,
            state -> MinecraftClient.getInstance().options.debugEnabled = state);

    public static <E> GameSetting<E> getSimpleOption(Supplier<E> supplier, Consumer<E> consumer) {
        return new GameSetting<>() {
            @Override
            public E get() {
                return supplier.get();
            }

            @Override
            public void set(E value) {
                consumer.accept(value);
            }
        };
    }

    public abstract T get();

    public abstract void set(T value);

    public static class GuiScaleSetting extends GameSetting<Integer> {
        public static GuiScaleSetting INSTANCE = new GuiScaleSetting();

        @Override
        public Integer get() {
            return MinecraftClient.getInstance().options.guiScale;
        }

        @Override
        public void set(Integer value) {
            MinecraftClient.getInstance().options.guiScale = value;
        }

        public double getScaleFactor() {
            return MinecraftClient.getInstance().getWindow().getScaleFactor();
        }
    }

}

