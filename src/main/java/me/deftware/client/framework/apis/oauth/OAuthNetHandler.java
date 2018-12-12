package me.deftware.client.framework.apis.oauth;

import me.deftware.mixin.imp.IMixinNetHandlerLoginClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.server.SPacketLoginSuccess;
import net.minecraft.util.text.ITextComponent;

public class OAuthNetHandler extends NetHandlerLoginClient {

	public OAuth.OAuthCallback callback;

	public OAuthNetHandler(NetworkManager networkManagerIn, Minecraft mcIn, GuiScreen previousScreenIn,
						   OAuth.OAuthCallback callback) {
		super(networkManagerIn, mcIn, previousScreenIn, (fakeConsumer) -> {});
		this.callback = callback;
	}

	@Override
	public void onDisconnect(ITextComponent reason) {
		callback.callback(false, "", "");
	}

	@Override
	public void handleLoginSuccess(SPacketLoginSuccess packetIn) {
		((IMixinNetHandlerLoginClient) this).setGameProfile(packetIn.getProfile());
		((IMixinNetHandlerLoginClient) this).getNetworkManager().setConnectionState(EnumConnectionState.PLAY);
		((IMixinNetHandlerLoginClient) this).getNetworkManager().setNetHandler(new OAuthNetHandlerPlayClient(Minecraft.getInstance(), null,
				((IMixinNetHandlerLoginClient) this).getNetworkManager(), ((IMixinNetHandlerLoginClient) this).getGameProfile(), callback));
	}

}
