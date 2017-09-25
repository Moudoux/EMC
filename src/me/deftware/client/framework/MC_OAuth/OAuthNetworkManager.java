package me.deftware.client.framework.MC_OAuth;

import java.net.InetAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import me.deftware.client.framework.MC_OAuth.OAuth.OAuthCallback;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NettyPacketDecoder;
import net.minecraft.network.NettyPacketEncoder;
import net.minecraft.network.NettyVarint21FrameDecoder;
import net.minecraft.network.NettyVarint21FrameEncoder;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.LazyLoadBase;

public class OAuthNetworkManager extends NetworkManager {

	private OAuthCallback callback;

	public OAuthNetworkManager(EnumPacketDirection packetDirection, OAuthCallback callback) {
		super(packetDirection);
		this.callback = callback;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_)
			throws Exception {
		callback.callback(false, "", "");
	}

	public static OAuthNetworkManager createNetworkManagerAndConnect(InetAddress address, int serverPort,
			boolean useNativeTransport, OAuthCallback callback) {
		final OAuthNetworkManager networkmanager = new OAuthNetworkManager(EnumPacketDirection.CLIENTBOUND, callback);
		Class<? extends SocketChannel> oclass;
		LazyLoadBase<? extends EventLoopGroup> lazyloadbase;

		if (Epoll.isAvailable() && useNativeTransport) {
			oclass = EpollSocketChannel.class;
			lazyloadbase = CLIENT_EPOLL_EVENTLOOP;
		} else {
			oclass = NioSocketChannel.class;
			lazyloadbase = CLIENT_NIO_EVENTLOOP;
		}

		(new Bootstrap()).group(lazyloadbase.getValue()).handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel p_initChannel_1_) throws Exception {
				try {
					p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
				} catch (ChannelException var3) {
					;
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
