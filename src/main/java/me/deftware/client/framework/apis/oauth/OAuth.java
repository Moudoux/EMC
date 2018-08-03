package me.deftware.client.framework.apis.oauth;

import net.minecraft.client.Minecraft;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.handshake.client.CPacketHandshake;
import net.minecraft.network.login.client.CPacketLoginStart;

import java.net.InetAddress;

/**
 * API to authenticate with https://mc-oauth.net/
 */
public class OAuth {

	private static final String ip = "srv.mc-oauth.net";
	private static final int port = 25565;

	public static void oAuth(OAuthCallback callback) {
		new Thread(() -> {
			try {
				InetAddress inetaddress = InetAddress.getByName(OAuth.ip);
				OAuthNetworkManager manager = OAuthNetworkManager.createNetworkManagerAndConnect(inetaddress, OAuth.port,
						Minecraft.getMinecraft().gameSettings.isUsingNativeTransport(), callback);
				manager.setNetHandler(new OAuthNetHandler(manager, Minecraft.getMinecraft(), null, callback));
				manager.sendPacket(new CPacketHandshake(OAuth.ip, OAuth.port, EnumConnectionState.LOGIN));
				manager.sendPacket(new CPacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
			} catch (Exception ex) {
				callback.callback(false, "", "");
			}
		}).start();
	}

	@FunctionalInterface
	public interface OAuthCallback {

		/**
		 * Called after oAuth attempt
		 *
		 * @param success If the user successfully authenticated
		 * @param code The users oAuth code
		 * @param time How long before the oAuth code expires
		 */
		void callback(boolean success, String code, String time);

	}

}
