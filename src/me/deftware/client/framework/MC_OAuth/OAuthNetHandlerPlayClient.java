package me.deftware.client.framework.MC_OAuth;

import com.mojang.authlib.GameProfile;

import me.deftware.client.framework.MC_OAuth.OAuth.OAuthCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketDisconnect;

public class OAuthNetHandlerPlayClient extends NetHandlerPlayClient {

	private OAuthCallback callback;

	public OAuthNetHandlerPlayClient(Minecraft mcIn, GuiScreen p_i46300_2_, NetworkManager networkManagerIn,
			GameProfile profileIn, OAuthCallback callback) {
		super(mcIn, p_i46300_2_, networkManagerIn, profileIn);
		this.callback = callback;
	}

	@Override
	public void handleDisconnect(SPacketDisconnect packetIn) {
		String code = packetIn.getReason().getUnformattedText().split("\n")[0].split("\"")[1].replace("\"", "");
		String time = packetIn.getReason().getUnformattedText().split("\n")[2]
				.substring("Your code will expire in ".length());
		callback.callback(true, code, time);
		this.netManager.closeChannel(packetIn.getReason());
	}

}
