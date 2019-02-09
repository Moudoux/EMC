package me.deftware.mixin.mixins;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import me.deftware.mixin.imp.IMixinNetworkPlayerInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ScoreboardEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ScoreboardEntry.class)
public class MixinNetworkPlayerInfo implements IMixinNetworkPlayerInfo {

    @Shadow
    @Final
    private Map<MinecraftProfileTexture.Type, Identifier> field_3742;

    @Final
    @Shadow
    private GameProfile profile;

    @Shadow
    private String field_3745;

    @Override
    public void reloadTextures() {
        field_3742.clear();
        MinecraftClient.getInstance().getSkinProvider().method_4652(this.profile, (p_210250_1_, p_210250_2_, p_210250_3_) -> {
            switch (p_210250_1_) {
                case SKIN:
                    this.field_3742.put(MinecraftProfileTexture.Type.SKIN, p_210250_2_);
                    this.field_3745 = p_210250_3_.getMetadata("model");
                    if (this.field_3745 == null) {
                        this.field_3745 = "default";
                    }
                    break;
                case CAPE:
                    this.field_3742.put(MinecraftProfileTexture.Type.CAPE, p_210250_2_);
                    break;
                case ELYTRA:
                    this.field_3742.put(MinecraftProfileTexture.Type.ELYTRA, p_210250_2_);
            }

        }, true);
    }

}
