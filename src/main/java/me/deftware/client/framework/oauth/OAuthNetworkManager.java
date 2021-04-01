package me.deftware.client.framework.oauth;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.minecraft.network.*;
import net.minecraft.util.Lazy;

import java.net.InetAddress;

/**
 * @author Deftware
 */
public class OAuthNetworkManager extends ClientConnection {

    private final OAuth.OAuthCallback callback;

    public OAuthNetworkManager(NetworkSide packetDirection, OAuth.OAuthCallback callback) {
        super(packetDirection);
        this.callback = callback;
    }

    public static OAuthNetworkManager connect(InetAddress address, int port, boolean useNativeTransport, OAuth.OAuthCallback callback) {
        final OAuthNetworkManager connection = new OAuthNetworkManager(NetworkSide.CLIENTBOUND, callback);
        Class<? extends Channel> socketClass;
        Lazy<?> lazyGroup;
        if (Epoll.isAvailable() && useNativeTransport) {
            socketClass = EpollSocketChannel.class;
            lazyGroup = EPOLL_CLIENT_IO_GROUP;
        } else {
            socketClass = NioSocketChannel.class;
            lazyGroup = CLIENT_IO_GROUP;
        }

        (new Bootstrap()).group((EventLoopGroup) lazyGroup.get()).handler(new ChannelInitializer<Channel>() {
            protected void initChannel(Channel channel) {
                try {
                    channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                } catch (ChannelException ignored) {
                }

                channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("splitter", new SplitterHandler()).addLast("decoder", new DecoderHandler(NetworkSide.CLIENTBOUND)).addLast("prepender", new SizePrepender()).addLast("encoder", new PacketEncoder(NetworkSide.SERVERBOUND)).addLast("packet_handler", connection);
            }
        }).channel(socketClass).connect(address, port).syncUninterruptibly();
        return connection;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable throwable) {
        callback.callback(false, "", "");
    }

}
