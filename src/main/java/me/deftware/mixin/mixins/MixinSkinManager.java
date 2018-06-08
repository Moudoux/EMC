package me.deftware.mixin.mixins;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.deftware.client.framework.maps.EMCSkinManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(SkinManager.class)
public abstract class MixinSkinManager {

	@Shadow
	public abstract ResourceLocation loadSkin(final MinecraftProfileTexture profileTexture, final MinecraftProfileTexture.Type textureType, @Nullable final SkinManager.SkinAvailableCallback skinAvailableCallback);

	@Inject(method = "loadProfileTextures", at = @At("HEAD"))
	public void loadProfileTextures(final GameProfile profile, final SkinManager.SkinAvailableCallback skinAvailableCallback, final boolean requireSecure, CallbackInfo cb) {
		Minecraft.getMinecraft().addScheduledTask(new Thread(() -> {
			try {
				String url = EMCSkinManager.getCape(profile.getName());
				if (!url.isEmpty()) {
					loadSkin(new MinecraftProfileTexture(url, null), MinecraftProfileTexture.Type.CAPE, skinAvailableCallback);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}));
	}

}
