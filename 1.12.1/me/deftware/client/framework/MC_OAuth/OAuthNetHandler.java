package me.deftware.client.framework.MC_OAuth;

import me.deftware.client.framework.MC_OAuth.OAuth.OAuthCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.server.SPacketLoginSuccess;
import net.minecraft.util.text.ITextComponent;

public class OAuthNetHandler extends NetHandlerLoginClient {

	public OAuthCallback callback;

	public OAuthNetHandler(NetworkManager networkManagerIn, Minecraft mcIn, GuiScreen previousScreenIn,
			OAuthCallback callback) {
		super(networkManagerIn, mcIn, previousScreenIn);
		this.callback = callback;
	}

	@Override
	public void onDisconnect(ITextComponent reason) {
		callback.callback(false, "", "");
	}

	@Override
	public void handleLoginSuccess(SPacketLoginSuccess packetIn) {
		this.gameProfile = packetIn.getProfile();
		this.networkManager.setConnectionState(EnumConnectionState.PLAY);
		this.networkManager.setNetHandler(new OAuthNetHandlerPlayClient(this.mc, this.previousGuiScreen,
				this.networkManager, this.gameProfile, callback));
	}

}
