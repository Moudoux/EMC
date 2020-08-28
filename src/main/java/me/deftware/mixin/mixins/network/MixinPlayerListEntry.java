
package me.deftware.mixin.mixins.network;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListEntry.class)
public class MixinPlayerListEntry {

    @Inject(method = "getGameMode", at = @At(value = "TAIL"), cancellable = true)
    private void onGetGameMode(CallbackInfoReturnable<GameMode> cir) {
        if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "FAKE_SPEC", false)) {
            cir.setReturnValue(GameMode.SPECTATOR);
        }
    }
}