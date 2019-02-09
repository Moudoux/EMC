package me.deftware.mixin.mixins;

import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.IServerData;
import net.minecraft.client.gui.menu.ServerConnectingScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerConnectingScreen.class)
public class MixinGuiConnecting {

    @Inject(method = "method_2130", at = @At("HEAD"))
    private void connect(String ip, int port, CallbackInfo ci) {
        IMinecraft.lastServer = new IServerData("Server", ip + ":" + port, false);
    }

}
