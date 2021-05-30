package me.deftware.mixin.mixins.render;

import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.client.framework.global.GameKeys;
import me.deftware.client.framework.global.GameMap;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RenderSystem.class)
public class MixinTextureManager {

    private static int syncedCount = 0;

    @ModifyVariable(method = "setShaderTexture(ILnet/minecraft/util/Identifier;)V", remap = false, at = @At("HEAD"))
    private static Identifier setShaderTexture(Identifier resource) {
        if (GameMap.INSTANCE.get(GameKeys.RAINBOW_ITEM_GLINT, false)) {
            if (resource.equals(ItemRenderer.ENCHANTED_ITEM_GLINT)) {
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
