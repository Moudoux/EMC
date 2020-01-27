package me.deftware.client.framework.apis.oauth;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.minecraft.network.*;
import net.minecraft.util.Lazy;

import java.net.InetAddress;

public class OAuthNetworkManager extends ClientConnection {

    private OAuth.OAuthCallback callback;

    public OAuthNetworkManager(NetworkSide packetDirection, OAuth.OAuthCallback callback) {
        super(packetDirection);
        this.callback = callback;
    }

    public static OAuthNetworkManager connect(InetAddress inetAddress_1, int int_1, boolean boolean_1, OAuth.OAuthCallback callback) {
        final OAuthNetworkManager clientConnection_1 = new OAuthNetworkManager(NetworkSide.CLIENTBOUND, callback);
        Class<? extends Channel> class_2;
        Lazy<?> lazy_2;
        if (Epoll.isAvailable() && boolean_1) {
            class_2 = EpollSocketChannel.class;
            lazy_2 = CLIENT_IO_GROUP_EPOLL;
        } else {
            class_2 = NioSocketChannel.class;
            lazy_2 = CLIENT_IO_GROUP;
        }

        (new Bootstrap()).group((EventLoopGroup) lazy_2.get()).handler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel channel_1) {
                try {
                    channel_1.config().setOption(ChannelOption.TCP_NODELAY, true);
                } catch (ChannelException ignored) {
                }

                channel_1.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("splitter", new SplitterHandler()).addLast("decoder", new DecoderHandler(NetworkSide.CLIENTBOUND)).addLast("prepender", new SizePrepender()).addLast("encoder", new PacketEncoder(NetworkSide.SERVERBOUND)).addLast("packet_handler", clientConnection_1);
            }
        }).channel(class_2).connect(inetAddress_1, int_1).syncUninterruptibly();
        return clientConnection_1;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) {
        callback.callback(false, "", "");
    }

}
