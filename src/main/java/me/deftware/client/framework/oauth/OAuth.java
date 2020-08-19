package me.deftware.client.framework.oauth;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.NetworkState;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;

import java.net.InetAddress;

/**
 * API to authenticate with https://mc-oauth.net/
 *
 * @author Deftware
 */
public class OAuth {

    private static final String ip = "srv.mc-oauth.net";
    private static final int port = 25565;

    public static void oAuth(OAuthCallback callback) {
        new Thread(() -> {
            Thread.currentThread().setName("OAuth thread");
            try {
                InetAddress inetaddress = InetAddress.getByName(OAuth.ip);
                OAuthNetworkManager manager = OAuthNetworkManager.connect(inetaddress, OAuth.port,
                        MinecraftClient.getInstance().options.useNativeTransport, callback);
                manager.setPacketListener(new OAuthNetHandler(manager, MinecraftClient.getInstance(), null, callback));
                manager.send(new HandshakeC2SPacket(OAuth.ip, OAuth.port, NetworkState.LOGIN));
                manager.send(new LoginHelloC2SPacket(MinecraftClient.getInstance().getSession().getProfile()));
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
         * @param code    The users oAuth code
         * @param time    How long before the oAuth code expires
         */
        void callback(boolean success, String code, String time);

    }

}
