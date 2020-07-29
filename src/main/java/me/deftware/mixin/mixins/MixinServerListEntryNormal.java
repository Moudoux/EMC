package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventServerPinged;
import me.deftware.client.framework.utils.ChatProcessor;
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

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public class MixinServerListEntryNormal {

    private boolean sentEvent = false;

    @Final
    @Shadow
    private ServerInfo server;

    @Inject(method = "render", at = @At("HEAD"))
    public void render(MatrixStack matrixStack, int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, boolean boolean_1, float float_1, CallbackInfo ci) {
        if (server.ping > 1 && !sentEvent) {
            sentEvent = true;
            StringBuilder population = new StringBuilder();
            if (server.playerListSummary != null) {
                for (Text text : server.playerListSummary) {
                    population.append(textToString(text)).append(", ");
                }
            }
            EventServerPinged event = new EventServerPinged(textToString(server.label), population.toString(), textToString(server.version), textToString(server.playerCountLabel), server.protocolVersion, server.ping);
            event.broadcast();
            server.label = stringToText(event.getServerMOTD());
            if (server.playerListSummary != null) {
                server.playerListSummary.clear();
                for (String player : event.getPlayerList().split(", ")) {
                    server.playerListSummary.add(stringToText(player));
                }
            }
            server.version = stringToText(event.getGameVersion());
            server.playerCountLabel = stringToText(event.getPopulationInfo());
            server.protocolVersion = event.getVersion();
            server.ping = event.getPingToServer();
        }
    }

    private String textToString(Text text) {
        if (text == null) return "";
        return ChatProcessor.getStringFromText(text);
    }

    private Text stringToText(String text) {
        return ChatProcessor.getLiteralText(text);
    }

}
