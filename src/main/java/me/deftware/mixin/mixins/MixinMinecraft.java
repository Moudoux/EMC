package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenDisplay;
import me.deftware.client.framework.event.events.EventShutdown;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraft implements IMixinMinecraft {

    @Shadow
    public boolean windowFocused;

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
    private static int currentFps;

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

    @Override
    public int getFPS() {
        return currentFps;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void runTick(CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof TitleScreen) {
            EventGuiScreenDisplay event = new EventGuiScreenDisplay(MinecraftClient.getInstance().currentScreen);
            event.broadcast();
            if (!(event.getScreen() instanceof TitleScreen)) {
                displayGuiScreen(event.getScreen());
            }
        }
    }

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public String getVersionType() {
        return "release";
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

    @Inject(method = "<init>*", at = @At("RETURN"))
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
        return MinecraftClient.getInstance().getWindow();
    }

    @Override
    public boolean getIsWindowFocused() {
        return windowFocused;
    }

}
