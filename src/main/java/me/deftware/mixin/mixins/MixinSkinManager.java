package me.deftware.mixin.mixins;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.utils.HashUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.*;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Mixin(SkinManager.class)
public abstract class MixinSkinManager {

    @Shadow
    @Final
    @Mutable
    private static ExecutorService THREAD_POOL;

    @Final
    @Shadow
    private MinecraftSessionService sessionService;

    @Shadow
    public abstract ResourceLocation loadSkin(final MinecraftProfileTexture profileTexture, final MinecraftProfileTexture.Type textureType, @Nullable final SkinManager.SkinAvailableCallback skinAvailableCallback);

    @Overwrite
    public void loadProfileTextures(GameProfile player, SkinManager.SkinAvailableCallback callback, boolean requireSecure) {
        THREAD_POOL.submit(() -> {
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textureMap = Maps.newHashMap();

            try {
                textureMap.putAll(this.sessionService.getTextures(player, requireSecure));
            } catch (InsecureTextureException var7) {
                //
            }

            if (textureMap.isEmpty()) {
                player.getProperties().clear();
                if (player.getId().equals(Minecraft.getInstance().getSession().getProfile().getId())) {
                    player.getProperties().putAll(Minecraft.getInstance().getProfileProperties());
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

            Minecraft.getInstance().addScheduledTask(() -> {
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
           String uidHash = HashUtils.getSHA(player.getId().toString().toLowerCase().replace("-", ""));
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
