package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventServerPinged;
import net.minecraft.client.gui.widget.MultiplayerServerListWidget;
import net.minecraft.client.options.ServerEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerServerListWidget.ServerItem.class)
public class MixinServerListEntryNormal {

    private boolean sentEvent = false;

    @Final
    @Shadow
    private ServerEntry server;

    @Inject(method = "render", at = @At("HEAD"))
    private void render(int p_194999_1_, int p_194999_2_, int p_194999_3_, int p_194999_4_, boolean p_194999_5_, float p_194999_6_, CallbackInfo ci) {
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
