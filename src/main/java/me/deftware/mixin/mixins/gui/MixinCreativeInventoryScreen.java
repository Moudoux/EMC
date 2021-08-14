package me.deftware.mixin.mixins.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(CreativeInventoryScreen.class)
public class MixinCreativeInventoryScreen {

    /**
     * Uses {@link MixinGuiScreen#onGetTooltipFromItem(ItemStack, CallbackInfoReturnable)}
     * to get a modified tooltip. Only called in the search tab in a creative inventory
     */
    @Redirect(method = "renderTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getTooltip(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/item/TooltipContext;)Ljava/util/List;"))
    private List<Text> onGetStackTooltip(ItemStack itemStack, PlayerEntity player, TooltipContext context) {
        return (((Screen) (Object) this).getTooltipFromItem(itemStack));
    }

}
