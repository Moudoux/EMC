package me.deftware.mixin.mixins;

import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.IServerData;
import net.minecraft.client.gui.GuiConnecting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting {

	@Inject(method = "connect", at = @At("HEAD"))
	private void connect(String ip, int port, CallbackInfo ci) {
		IMinecraft.lastServer = new IServerData("Server", ip + ":" + port, false);
	}

}
