package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventServerPinged;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public class MixinServerListEntryNormal {

    private boolean sentEvent = false;

    @Final
    @Shadow
    private ServerInfo server;

    @Inject(method = "render", at = @At("HEAD"))
    public void render(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, boolean boolean_1, float float_1, CallbackInfo ci) {
        if (server.ping > 1 && !sentEvent) {
            sentEvent = true;
            EventServerPinged event = new EventServerPinged(server.label, server.playerListSummary,
                    server.version, server.playerCountLabel, server.protocolVersion, server.ping);
            event.broadcast();
            server.label = event.getServerMOTD();
            server.playerListSummary = event.getPlayerList();
            server.version = event.getGameVersion();
            server.playerCountLabel = event.getPopulationInfo();
            server.protocolVersion = event.getVersion();
            server.ping = event.getPingToServer();
        }
    }

}
