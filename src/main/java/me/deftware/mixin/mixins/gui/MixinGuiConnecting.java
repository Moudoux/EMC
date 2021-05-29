package me.deftware.mixin.mixins.gui;

import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.util.minecraft.ServerConnectionInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class MixinGuiConnecting {

    @Inject(method = "connect(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;)V", at = @At("HEAD"))
    private void connect(MinecraftClient client, ServerAddress address, CallbackInfo ci) {
        Minecraft.lastConnectedServer = new ServerConnectionInfo("Server", address.getAddress() + ":" + address.getPort(), false);
    }

}
