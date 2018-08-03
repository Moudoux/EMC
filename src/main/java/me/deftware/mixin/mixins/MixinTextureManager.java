package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TextureManager.class)
public class MixinTextureManager {

	@ModifyVariable(method = "bindTexture", at = @At("HEAD"))
	private ResourceLocation bindTexture(ResourceLocation resource) {
		if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "RAINBOW_ITEM_GLINT", false)) {
			if (resource.getPath().contains("enchanted_item_glint")) {
				resource = new ResourceLocation(
						"emc/enchanted_item_glint_rainbow.png");
			}
		}
		return resource;
	}

}
