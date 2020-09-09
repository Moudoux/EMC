package me.deftware.mixin.mixins.gui;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.events.EventGetItemToolTip;
import me.deftware.client.framework.item.Item;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(CreativeInventoryScreen.class)
public abstract class MixinCreativeInventoryScreen extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {

    @Unique
    private ItemStack stack;

    @Inject(at = @At("HEAD"), method="renderTooltip")
    public void onRenderTooolip(MatrixStack matrices, ItemStack stack, int x, int y, CallbackInfo ci) {
        this.stack = stack;
    }


    /*
    TODO: find a better way of doing this.
    ie. use ModifyVariable on renderTooltip(MatrixStack matrices, List<Text> lines, int x, int y)
    inside of
    renderTooolip(MatrixStack matrices, ItemStack stack, int x, int y);
     */

    @Override
    public void renderTooltip(MatrixStack matrices, List<Text> lines, int x, int y) {
        List<ChatMessage> list = new ArrayList<>();
        for (Text text : lines) {
            list.add(new ChatMessage().fromText(text));
        }
        EventGetItemToolTip event = new EventGetItemToolTip(list, Item.newInstance(stack.getItem()), client.options.advancedItemTooltips);
        event.broadcast();
        List<Text> modifiedTextList = new ArrayList<>();
        for (ChatMessage text : event.getList()) {
            modifiedTextList.add(text.build());
        }
        super.renderTooltip(matrices, modifiedTextList, x, y);
    }



    public MixinCreativeInventoryScreen(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }
}
