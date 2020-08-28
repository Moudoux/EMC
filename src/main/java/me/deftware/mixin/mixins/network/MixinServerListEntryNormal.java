package me.deftware.mixin.mixins.network;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.events.EventServerPinged;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public class MixinServerListEntryNormal {

    private boolean sentEvent = false;

    @Final
    @Shadow
    private ServerInfo server;

    @Inject(method = "render", at = @At("HEAD"))
    public void render(MatrixStack matrixStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        if (server.ping > 1 && !sentEvent) {
            sentEvent = true;
            List<ChatMessage> populationInfo = new ArrayList<>();
            if (server.playerListSummary != null) {
                for (Text text : server.playerListSummary) {
                    populationInfo.add(new ChatMessage().fromText(text));
                }
            }
            EventServerPinged event = new EventServerPinged(
                    new ChatMessage().fromText(server.label),
                    new ChatMessage().fromText(server.playerCountLabel),
                    new ChatMessage().fromText(server.version),
                    populationInfo,
                    server.protocolVersion,
                    server.ping
            );
            event.broadcast();
            server.label = event.getServerMOTD().build();
            if (server.playerListSummary != null) {
                server.playerListSummary.clear();
                for (ChatMessage player : event.getPopulationInfo()) {
                    server.playerListSummary.add(player.build());
                }
            }
            server.version = event.getGameVersion().build();
            server.playerCountLabel = event.getPlayerList().build();
            server.protocolVersion = event.getVersion();
            server.ping = event.getPingToServer();
        }
    }

}
