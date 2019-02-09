package me.deftware.client.framework.apis.oauth;

import me.deftware.mixin.imp.IMixinNetHandlerLoginClient;
import net.minecraft.class_2901;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.text.TextComponent;

public class OAuthNetHandler extends ClientLoginNetworkHandler {

    public OAuth.OAuthCallback callback;

    public OAuthNetHandler(ClientConnection networkManagerIn, MinecraftClient mcIn, Screen previousScreenIn,
                           OAuth.OAuthCallback callback) {
        super(networkManagerIn, mcIn, previousScreenIn, (fakeConsumer) -> {
        });
        this.callback = callback;
    }

    @Override
    public void onConnectionLost(TextComponent reason) {
        callback.callback(false, "", "");
    }

    @Override
    public void method_12588(class_2901 packetIn) {
        ((IMixinNetHandlerLoginClient) this).setGameProfile(packetIn.method_12593());
        ((IMixinNetHandlerLoginClient) this).getNetworkManager().setState(NetworkState.GAME);
        ((IMixinNetHandlerLoginClient) this).getNetworkManager().setPacketListener(new OAuthNetHandlerPlayClient(MinecraftClient.getInstance(), null,
                ((IMixinNetHandlerLoginClient) this).getNetworkManager(), ((IMixinNetHandlerLoginClient) this).getGameProfile(), callback));
    }

}
