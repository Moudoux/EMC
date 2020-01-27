package me.deftware.mixin.mixins;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.utils.HashUtils;
import net.minecraft.client.texture.PlayerSkinProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(PlayerSkinProvider.class)
public abstract class MixinSkinManager {
     @Inject(method = "*",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getInstance()Lnet/minecraft/client/MinecraftClient;", ordinal = 1),
             locals = LocalCapture.CAPTURE_FAILHARD)
     private void onLoadSkin(GameProfile gameProfile, boolean bl, PlayerSkinProvider.SkinTextureAvailableCallback skinTextureAvailableCallback, CallbackInfo ci, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map) {
        injectCape(gameProfile, map);
    }

    private void injectCape(GameProfile profile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map) {
        try {
            String uidHash = HashUtils.getSHA(profile.getId().toString().replace("-", "")).toLowerCase();
            String id = SettingsMap.hasValue(SettingsMap.MapKeys.CAPES_TEXTURE, profile.getName()) ? profile.getName() :
                    SettingsMap.hasValue(SettingsMap.MapKeys.CAPES_TEXTURE, profile.getId().toString().replace("-", ""))
                            ? profile.getId().toString().replace("-", "") : SettingsMap.hasValue(SettingsMap.MapKeys.CAPES_TEXTURE, uidHash) ? uidHash : null;
            if (id != null) {
                map.put(MinecraftProfileTexture.Type.CAPE, new MinecraftProfileTexture(
                        (String) SettingsMap.getValue(SettingsMap.MapKeys.CAPES_TEXTURE, id, ""), null));
            }
        } catch (Exception ex) {
            Bootstrap.logger.error("Failed to load skin");
        }
    }
}
