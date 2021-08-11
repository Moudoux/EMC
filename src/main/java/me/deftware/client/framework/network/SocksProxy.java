package me.deftware.client.framework.network;

import io.netty.handler.proxy.ProxyHandler;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;

/**
 * @author Deftware
 */
public interface SocksProxy {

	String getUsername();

	String getPassword();

	String getAddress();

	default InetSocketAddress getSocketAddress() {
		if (getAddress().contains(":")) {
			String[] data = getAddress().split(":");
			return new InetSocketAddress(data[0], Integer.parseInt(data[1]));
		}
		return new InetSocketAddress(getAddress(), 1080);
	}

	/**
	 * @return The socks proxy version
	 */
	default int getVersion() {
		return StringUtils.isEmpty(getPassword()) ? 4 : 5;
	}

	/**
	 * @return A proxy handler instance
	 */
	default ProxyHandler getProxyHandler() {
		if (getVersion() == 4) {
			return new Socks4ProxyHandler(getSocketAddress(), getUsername());
		}
		return new Socks5ProxyHandler(getSocketAddress(), getUsername(), getPassword());
	}

}
