package me.deftware.client.framework.apis.oauth;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketDisconnect;

public class OAuthNetHandlerPlayClient extends NetHandlerPlayClient {

	private OAuth.OAuthCallback callback;

	public OAuthNetHandlerPlayClient(Minecraft mcIn, GuiScreen p_i46300_2_, NetworkManager networkManagerIn,
									 GameProfile profileIn, OAuth.OAuthCallback callback) {
		super(mcIn, p_i46300_2_, networkManagerIn, profileIn);
		this.callback = callback;
	}

	@Override
	public void handleDisconnect(SPacketDisconnect packetIn) {
		String code = packetIn.getReason().getUnformattedComponentText().split("\n")[0].split("\"")[1].replace("\"", "");
		String time = packetIn.getReason().getUnformattedComponentText().split("\n")[2]
				.substring("Your code will expire in ".length());
		callback.callback(true, code, time);
		getNetworkManager().closeChannel(packetIn.getReason());
	}

}
