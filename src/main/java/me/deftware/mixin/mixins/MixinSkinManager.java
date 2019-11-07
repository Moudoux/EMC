package me.deftware.mixin.mixins;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.utils.HashUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.*;

import java.util.Map;
import java.util.concurrent.ExecutorService;

@Mixin(PlayerSkinProvider.class)
public abstract class MixinSkinManager {

    @Final
    @Shadow
    private MinecraftSessionService sessionService;

    @Shadow
    public abstract Identifier loadSkin( MinecraftProfileTexture minecraftProfileTexture_1, MinecraftProfileTexture.Type minecraftProfileTexture$Type_1, PlayerSkinProvider.SkinTextureAvailableCallback playerSkinProvider$class_1072_1);

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public void loadSkin(GameProfile player, PlayerSkinProvider.SkinTextureAvailableCallback callback, boolean requireSecure) {
        Util.getServerWorkerExecutor().execute(() -> {
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textureMap = Maps.newHashMap();

            try {
                textureMap.putAll(this.sessionService.getTextures(player, requireSecure));
            } catch (InsecureTextureException var7) {
                //
            }

            if (textureMap.isEmpty()) {
                player.getProperties().clear();
                if (player.getId().equals(MinecraftClient.getInstance().getSession().getProfile().getId())) {
                    player.getProperties().putAll(MinecraftClient.getInstance().getSessionProperties());
                    textureMap.putAll(this.sessionService.getTextures(player, false));
                } else {
                    this.sessionService.fillProfileProperties(player, requireSecure);

                    try {
                        textureMap.putAll(this.sessionService.getTextures(player, requireSecure));
                    } catch (InsecureTextureException var6) {
                        //
                    }
                }
            }

            injectCape(player, textureMap);

            MinecraftClient.getInstance().execute(() -> {
                if (textureMap.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                    this.loadSkin((MinecraftProfileTexture) textureMap.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, callback);
                }

                if (textureMap.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                    this.loadSkin((MinecraftProfileTexture) textureMap.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, callback);
                }

            });
        });
    }

    private void injectCape(GameProfile player, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map) {
        try {
            String uidHash = HashUtils.getSHA(player.getId().toString().replace("-", "")).toLowerCase();
            String id = SettingsMap.hasValue(SettingsMap.MapKeys.CAPES_TEXTURE, player.getName()) ? player.getName() :
                    SettingsMap.hasValue(SettingsMap.MapKeys.CAPES_TEXTURE, player.getId().toString().replace("-", ""))
                            ? player.getId().toString().replace("-", "") : SettingsMap.hasValue(SettingsMap.MapKeys.CAPES_TEXTURE, uidHash) ? uidHash : null;
            if (id != null) {
                map.put(MinecraftProfileTexture.Type.CAPE, new MinecraftProfileTexture(
                        (String) SettingsMap.getValue(SettingsMap.MapKeys.CAPES_TEXTURE, id, ""), null));
            }
        } catch (Exception ex) {
            Bootstrap.logger.error("Failed to load skin");
        }
    }

}
