package me.deftware.mixin.mixins.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.deftware.mixin.imp.IMixinNetworkPlayerInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(PlayerListEntry.class)
public class MixinNetworkPlayerInfo implements IMixinNetworkPlayerInfo {

    @Shadow
    @Final
    private Map<MinecraftProfileTexture.Type, Identifier> textures;

    @Final
    @Shadow
    private GameProfile profile;

    @Shadow
    private String model;

    @Override
    public void reloadTextures() {
        textures.clear();
        MinecraftClient.getInstance().getSkinProvider().loadSkin(this.profile, (type, id, texture) -> {
            switch (type) {
                case SKIN:
                    this.textures.put(MinecraftProfileTexture.Type.SKIN, id);
                    this.model = texture.getMetadata("model");
                    if (this.model == null) {
                        this.model = "default";
                    }
                    break;
                case CAPE:
                    this.textures.put(MinecraftProfileTexture.Type.CAPE, id);
                    break;
                case ELYTRA:
                    this.textures.put(MinecraftProfileTexture.Type.ELYTRA, id);
            }

        }, true);
    }

}
