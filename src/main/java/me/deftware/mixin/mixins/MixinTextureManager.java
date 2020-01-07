package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TextureManager.class)
public class MixinTextureManager {

    int syncedCount = 0;

    @ModifyVariable(method = "bindTexture", at = @At("HEAD"))
    private Identifier bindTexture(Identifier resource) {
        if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "RAINBOW_ITEM_GLINT", false)) {
            if (resource.getPath().contains("enchanted_item_glint")) {
                if (syncedCount > 50) { // One tick as Normal Enchant Glint
                    resource = new Identifier(
                            "emc/enchanted_item_glint_rainbow.png");
                } else {
                    syncedCount++;
                }
            }
        }
        return resource;
    }

}
