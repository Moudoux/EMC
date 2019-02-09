package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSlowdown;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

@Mixin(KeyboardInput.class)
public class MixinMovementInputFromOptions {



}
