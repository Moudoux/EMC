package me.deftware.mixin.mixins;

import me.deftware.client.framework.utils.OSUtils;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Main.class)
public class MixinMain {

    @Inject(method = "main", at = @At("HEAD"), remap = false)
    private static void main(String[] args, CallbackInfo ci) {

    }

}
