package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenDisplay;
import me.deftware.client.framework.event.events.EventShutdown;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.utils.INonNullList;
import me.deftware.client.framework.utils.exception.ExceptionHandler;
import me.deftware.client.framework.utils.exception.GlUtil;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.OutOfMemoryScreen;
import net.minecraft.client.gui.screen.SaveLevelScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.Window;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraft implements IMixinMinecraft {

    private static Queue<Runnable> staticRenderTaskQueue;

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

    @Inject(method = "render", at = @At("TAIL"))
    private void render(boolean tick, CallbackInfo ci) {
        staticRenderTaskQueue = renderTaskQueue;
    }

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public static void printCrashReport(CrashReport crashReport) {
        exception(null, crashReport, false);
    }

    @Shadow
    @Final
    private Queue<Runnable> renderTaskQueue;

    private static void exception(Throwable e, CrashReport report, boolean unexpected) {
        // Credit to https://github.com/natanfudge/Not-Enough-Crashes/blob/master/notenoughcrashes/src/main/java/fudge/notenoughcrashes/mixinhandlers/InGameCatcher.java
        Integer originalReservedMemorySize = null;
        try {
            if (MinecraftClient.memoryReservedForCrash != null) {
                originalReservedMemorySize = MinecraftClient.memoryReservedForCrash.length;
                MinecraftClient.memoryReservedForCrash = new byte[0];
            }
        } catch (Throwable ignored) { }
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            MinecraftClient.getInstance().getNetworkHandler().getConnection().disconnect(new LiteralText("Crashed"));
        }
        MinecraftClient.getInstance().disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
        staticRenderTaskQueue.clear();
        GlUtil.resetState();
        if (originalReservedMemorySize != null) {
            try {
                MinecraftClient.memoryReservedForCrash = new byte[originalReservedMemorySize];
            } catch (Throwable ignored) { }
        }
        System.gc();
        MinecraftClient.getInstance().options.debugEnabled = false;
        MinecraftClient.getInstance().inGameHud.getChatHud().clear(true);
        ExceptionHandler.handleCrash(e, report, unexpected, customPrintCrashReport(report));
    }

    private static File customPrintCrashReport(CrashReport crashReport) {
        File file = new File(MinecraftClient.getInstance().runDirectory, "crash-reports");
        File file2 = new File(file, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
        net.minecraft.Bootstrap.println(crashReport.asString());
        if (crashReport.getFile() != null) {
            net.minecraft.Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReport.getFile());
            return crashReport.getFile();
        } else if (crashReport.writeToFile(file2)) {
            net.minecraft.Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
            return file2;
        } else {
            net.minecraft.Bootstrap.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
        }
        return null;
    }


    /**
     * @reason Removes the modded flag
     * @author deftware
     */
    @Overwrite
    public boolean method_24289() {
        return false;
    }

}
