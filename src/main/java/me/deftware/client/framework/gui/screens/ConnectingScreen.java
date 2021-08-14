package me.deftware.client.framework.gui.screens;

import me.deftware.client.framework.minecraft.ServerDetails;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;

/**
 * @since 17.0.0
 * @author Deftware
 */
public interface ConnectingScreen {

	/**
	 * Connects to a Minecraft server
	 *
	 * @param server Server details
	 */
	static void _connect(ServerDetails server) {
		if (server != null) {
			ConnectScreen.connect(
					new MultiplayerScreen(
							MinecraftClient.getInstance().currentScreen
					), MinecraftClient.getInstance(), ServerAddress.parse(server._getAddress()), (ServerInfo) server
			);
		}
	}

}
