package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventServerPinged;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerListEntryNormal.class)
public class MixinServerListEntryNormal {

	private boolean sentEvent = false;

	@Final
	@Shadow
	private ServerData server;

	@Inject(method = "drawEntry", at = @At("HEAD"))
	private void drawEntry(int p_194999_1_, int p_194999_2_, int p_194999_3_, int p_194999_4_, boolean p_194999_5_, float p_194999_6_, CallbackInfo ci) {
		if (server.pingToServer > 1 && !sentEvent) {
			sentEvent = true;
			EventServerPinged event = new EventServerPinged(server.serverMOTD, server.playerList,
					server.gameVersion, server.populationInfo, server.version, server.pingToServer);
			event.broadcast();
			server.serverMOTD = event.getServerMOTD();
			server.playerList = event.getPlayerList();
			server.gameVersion = event.getGameVersion();
			server.populationInfo = event.getPopulationInfo();
			server.version = event.getVersion();
			server.pingToServer = event.getPingToServer();
		}
	}

}
