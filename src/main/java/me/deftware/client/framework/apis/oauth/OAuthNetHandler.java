package me.deftware.client.framework.apis.oauth;

import me.deftware.mixin.imp.IMixinNetHandlerLoginClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.network.packet.LoginSuccessS2CPacket;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.text.Text;

public class OAuthNetHandler extends ClientLoginNetworkHandler {

    public OAuth.OAuthCallback callback;

    public OAuthNetHandler(ClientConnection networkManagerIn, MinecraftClient mcIn, Screen previousScreenIn,
                           OAuth.OAuthCallback callback) {
        super(networkManagerIn, mcIn, previousScreenIn, (fakeConsumer) -> {
        });
        this.callback = callback;
    }

    @Override
    public void onDisconnected(Text reason) {
        callback.callback(false, "", "");
    }

    @Override
    public void onLoginSuccess(LoginSuccessS2CPacket packetIn) {
        ((IMixinNetHandlerLoginClient) this).setGameProfile(packetIn.getProfile());
        ((IMixinNetHandlerLoginClient) this).getNetworkManager().setState(NetworkState.PLAY);
        ((IMixinNetHandlerLoginClient) this).getNetworkManager().setPacketListener(new OAuthNetHandlerPlayClient(MinecraftClient.getInstance(), null,
                ((IMixinNetHandlerLoginClient) this).getNetworkManager(), ((IMixinNetHandlerLoginClient) this).getGameProfile(), callback));
    }

}
