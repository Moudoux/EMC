package me.deftware.client.framework.Server;

import java.net.InetAddress;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;

/**
 * Used for connecting to MC-OAuth in the background
 * 
 * @author deftware
 *
 */
public class SilentJoin {

	public static void connect() {
		new Thread("SlientJoin") {
			@Override
			public void run() {
			}
		}.start();
	}

}
