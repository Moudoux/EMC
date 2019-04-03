package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenPostDraw;
import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.IServerData;
import net.minecraft.client.gui.GuiConnecting;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting extends MixinGuiScreen {

	@Inject(method = "connect", at = @At("HEAD"))
	private void connect(String ip, int port, CallbackInfo ci) {
		IMinecraft.lastServer = new IServerData("Server", ip + ":" + port, false);
	}

	@Inject(method = "<init>*", at = @At("RETURN"))
	private void onConstructed(CallbackInfo ci) {
		this.shouldSendPostRenderEvent = false;
	}

	@Inject(method = "render", at = @At("RETURN"), remap = false)
	public void render_return(int x, int y, float p_render_3_, CallbackInfo ci) {
		new EventGuiScreenPostDraw((GuiScreen) (Object) this, x, y).broadcast();
	}


}
