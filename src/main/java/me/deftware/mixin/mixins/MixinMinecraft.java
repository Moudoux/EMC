package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenDisplay;
import me.deftware.client.framework.event.events.EventShutdown;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.MainMenuScreen;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Window;
import net.minecraft.util.Session;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public abstract class MixinMinecraft implements IMixinMinecraft {

    @Mutable
    @Shadow
    @Final
    private Session session;

    @Shadow
    @Final
    private RenderTickCounter renderTickCounter;

    @Shadow
    private Screen currentScreen;

    @Shadow
    private int itemUseCooldown;


    @Shadow
    public abstract void openScreen(Screen screen);

    @Override
    public void displayGuiScreen(Screen guiScreenIn) {
        openScreen(guiScreenIn);
    }

    @Shadow
    public abstract void doAttack();

    @Shadow
    public abstract void doItemUse();

    @Shadow
    public abstract void doItemPick();

    @ModifyVariable(method = "openScreen", at = @At("HEAD"))
    private Screen displayGuiScreenModifier(Screen screen) {
        EventGuiScreenDisplay event = new EventGuiScreenDisplay(screen);
        event.broadcast();
        return event.isCanceled() ? null : event.getScreen();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void runTick(CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof MainMenuScreen) {
            EventGuiScreenDisplay event = new EventGuiScreenDisplay(MinecraftClient.getInstance().currentScreen);
            event.broadcast();
            if (!(event.getScreen() instanceof MainMenuScreen)) {
                displayGuiScreen(event.getScreen());
            }
        }
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void shutdownMinecraftApplet(CallbackInfo ci) {
        new EventShutdown().broadcast();
        Bootstrap.isRunning = false;
    }

    @Override
    public void setRightClickDelayTimer(int delay) {
        this.itemUseCooldown = delay;
    }

    @Override
    public void doClickMouse() {
        doAttack();
    }

    @Override
    public void doRightClickMouse() {
        doItemUse();
    }

    @Override
    public void doMiddleClickMouse() {
        doItemPick();
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void init(CallbackInfo callbackInfo) {
        Bootstrap.init();
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public RenderTickCounter getTimer() {
        return renderTickCounter;
    }

    @Override
    public Window getMainWindow() {
        return MinecraftClient.getInstance().window;
    }

}
