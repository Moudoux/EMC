package me.deftware.mixin.mixins;

import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.util.minecraft.ServerConnectionInfo;
import net.minecraft.client.gui.screen.ConnectScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class MixinGuiConnecting {

    @Inject(method = "connect", at = @At("HEAD"))
    private void connect(String ip, int port, CallbackInfo ci) {
        Minecraft.lastConnectedServer = new ServerConnectionInfo("Server", ip + ":" + port, false);
    }

}
