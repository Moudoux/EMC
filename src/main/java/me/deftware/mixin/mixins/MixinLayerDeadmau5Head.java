package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.render.entity.feature.Deadmau5FeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Deadmau5FeatureRenderer.class)
public class MixinLayerDeadmau5Head {
    /**
     * @author Deftware
     * @reason
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/String;equals(Ljava/lang/Object;)Z"))
    private boolean render(String deadmau5, Object playerName) {
        String usernames = (String) SettingsMap.getValue(SettingsMap.MapKeys.MISC, "DEADMAU_EARS", "");
        for (String username : usernames.split(",")) {
            if (username.equalsIgnoreCase(playerName.toString())) {
                return true;
            }
        }
        return deadmau5.equals(playerName);
    }
}
