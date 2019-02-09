package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventServerPinged;
import net.minecraft.client.gui.widget.RemoteServerListEntry;
import net.minecraft.client.options.ServerEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RemoteServerListEntry.class)
public class MixinServerListEntryNormal {

    private boolean sentEvent = false;

    @Final
    @Shadow
    private ServerEntry serverEntry;

    @Inject(method = "draw", at = @At("HEAD"))
    private void draw(int p_194999_1_, int p_194999_2_, int p_194999_3_, int p_194999_4_, boolean p_194999_5_, float p_194999_6_, CallbackInfo ci) {
        if (serverEntry.ping > 1 && !sentEvent) {
            sentEvent = true;
            EventServerPinged event = new EventServerPinged(serverEntry.label, serverEntry.playerListSummary,
                    serverEntry.version, serverEntry.playerCountLabel, serverEntry.protocolVersion, serverEntry.ping).send();
            serverEntry.label = event.getServerMOTD();
            serverEntry.playerListSummary = event.getPlayerList();
            serverEntry.version = event.getGameVersion();
            serverEntry.playerCountLabel = event.getPopulationInfo();
            serverEntry.protocolVersion = event.getVersion();
            serverEntry.ping = event.getPingToServer();
        }
    }

}
