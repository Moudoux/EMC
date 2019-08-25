package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerListEntry.class)
public class MixinPlayerListEntry {

    @Shadow
    private GameMode gameMode;

    @Overwrite
    public GameMode getGameMode() {
        if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "FAKE_SPEC", false)) {
            return GameMode.SPECTATOR;
        }
        return gameMode;
    }

}
