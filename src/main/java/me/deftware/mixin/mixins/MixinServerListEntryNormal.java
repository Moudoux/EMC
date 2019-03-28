package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventServerPinged;
import net.minecraft.class_4267;
import net.minecraft.client.options.ServerEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_4267.class_4270.class)
public class MixinServerListEntryNormal {

    private boolean sentEvent = false;

    @Final
    @Shadow
    private ServerEntry field_19120;

    @Inject(method = "draw", at = @At("HEAD"))
    private void draw(int p_194999_1_, int p_194999_2_, int p_194999_3_, int p_194999_4_, boolean p_194999_5_, float p_194999_6_, CallbackInfo ci) {
        if (field_19120.ping > 1 && !sentEvent) {
            sentEvent = true;
            EventServerPinged event = new EventServerPinged(field_19120.label, field_19120.playerListSummary,
                    field_19120.version, field_19120.playerCountLabel, field_19120.protocolVersion, field_19120.ping);
            event.broadcast();
            field_19120.label = event.getServerMOTD();
            field_19120.playerListSummary = event.getPlayerList();
            field_19120.version = event.getGameVersion();
            field_19120.playerCountLabel = event.getPopulationInfo();
            field_19120.protocolVersion = event.getVersion();
            field_19120.ping = event.getPingToServer();
        }
    }

}
