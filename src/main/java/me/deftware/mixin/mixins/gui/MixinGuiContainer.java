package me.deftware.mixin.mixins.gui;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.gui.screens.ContainerScreen;
import me.deftware.client.framework.inventory.Inventory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Lazy;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class MixinGuiContainer<T extends ScreenHandler> extends MixinGuiScreen implements ContainerScreen {

    @Shadow
    protected Slot focusedSlot;

    @Shadow
    @Final
    protected T handler;

    @Unique
    private final Lazy<Inventory> inventoryLazy = new Lazy<>(() -> new Inventory(
            getHandlerInventory()
    ));

    @Override
    public Slot getMinecraftSlot() {
        return focusedSlot;
    }

    @Override
    public ScreenHandler getScreenHandler() {
        return handler;
    }

    @Override
    public Inventory getContainerInventory() {
        if (getHandlerInventory() == null)
            return null;
        return inventoryLazy.get();
    }

    @Unique
    private final Lazy<ChatMessage> inventoryTitle = new Lazy<>(() -> new ChatMessage().fromText(
            ((Screen) (Object) this).getTitle()
    ));

    @Override
    public ChatMessage getInventoryName() {
        return inventoryTitle.get();
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void onPostDraw(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.onPostDrawEvent(matrices, mouseX, mouseY, delta);
    }

}
