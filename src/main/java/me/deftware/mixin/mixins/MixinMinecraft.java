package me.deftware.mixin.mixins;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import me.deftware.client.framework.event.events.EventGuiScreenDisplay;
import me.deftware.client.framework.event.events.EventShutdown;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraft implements IMixinMinecraft {

    @Shadow
    private boolean windowFocused;

    @Mutable
    @Shadow
    @Final
    private Session session;

    @Shadow
    @Final
    private RenderTickCounter renderTickCounter;

    @Shadow
    private int itemUseCooldown;

    @Shadow
    private static int currentFps;

    @Shadow
    @Final
    @Mutable
    private MinecraftSessionService sessionService;

    @Shadow
    public abstract void openScreen(Screen screen);

    @Override
    public void displayGuiScreen(Screen guiScreenIn) {
        openScreen(guiScreenIn);
    }

    @Shadow
    protected abstract void doAttack();

    @Shadow
    protected abstract void doItemUse();

    @Shadow
    protected abstract void doItemPick();

    @ModifyVariable(method = "openScreen", at = @At("HEAD"))
    private Screen displayGuiScreenModifier(Screen screen) {
        EventGuiScreenDisplay event = new EventGuiScreenDisplay(screen);
        event.broadcast();
        return event.isCanceled() ? null : event.getScreen();
    }

    @Override
    public void setSessionService(MinecraftSessionService service) {
        sessionService = service;
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

    @Inject(method = "getVersionType", at = @At("HEAD"), cancellable = true)
    private void onGetVersionType(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("release");
    }

    @Inject(method = "getGameVersion", at = @At("TAIL"), cancellable = true)
    private void onGetGameVersion(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(SharedConstants.getGameVersion().getName());
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

    @Inject(method = "isModded", at = @At(value = "TAIL"), cancellable = true)
    public void isModdedCheck(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

}
