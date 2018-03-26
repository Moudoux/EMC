package me.deftware.client.framework.apis.oauth;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.minecraft.network.*;
import net.minecraft.util.LazyLoadBase;

import java.net.InetAddress;

public class OAuthNetworkManager extends NetworkManager {

	private OAuth.OAuthCallback callback;

	public OAuthNetworkManager(EnumPacketDirection packetDirection, OAuth.OAuthCallback callback) {
		super(packetDirection);
		this.callback = callback;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_)
			throws Exception {
		callback.callback(false, "", "");
	}

	public static OAuthNetworkManager createNetworkManagerAndConnect(InetAddress address, int serverPort,
																	 boolean useNativeTransport, OAuth.OAuthCallback callback) {
		OAuthNetworkManager networkmanager = new OAuthNetworkManager(EnumPacketDirection.CLIENTBOUND, callback);
		Class<? extends SocketChannel> oclass;
		LazyLoadBase<? extends EventLoopGroup> lazyloadbase;

		if (Epoll.isAvailable() && useNativeTransport) {
			oclass = EpollSocketChannel.class;
			lazyloadbase = NetworkManager.CLIENT_EPOLL_EVENTLOOP;
		} else {
			oclass = NioSocketChannel.class;
			lazyloadbase = NetworkManager.CLIENT_NIO_EVENTLOOP;
		}

		(new Bootstrap()).group(lazyloadbase.getValue()).handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel p_initChannel_1_) throws Exception {
				try {
					p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
				} catch (ChannelException var3) {
				}

				p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(30))
						.addLast("splitter", new NettyVarint21FrameDecoder())
						.addLast("decoder", new NettyPacketDecoder(EnumPacketDirection.CLIENTBOUND))
						.addLast("prepender", new NettyVarint21FrameEncoder())
						.addLast("encoder", new NettyPacketEncoder(EnumPacketDirection.SERVERBOUND))
						.addLast("packet_handler", networkmanager);
			}
		}).channel(oclass).connect(address, serverPort).syncUninterruptibly();
		return networkmanager;
	}

}
