package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenDisplay;
import me.deftware.client.framework.event.events.EventShutdown;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.utils.exception.ExceptionHandler;
import me.deftware.mixin.imp.IMixinMinecraft;
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

    @Shadow
    private Thread thread;

    @Shadow
    private boolean running;

    @Shadow
    protected abstract void render(boolean tick);

    @Shadow
    private CrashReport crashReport;

    /**
     * @author deftware
     */
    @Overwrite
    public void run() {
        this.thread = Thread.currentThread();
        try {
            boolean bl = false;
            while(this.running) {
                try {
                    try {
                        this.render(!bl);
                    } catch (OutOfMemoryError var3) {
                        this.cleanUpAfterCrash();
                        this.openScreen(new OutOfMemoryScreen());
                        System.gc();
                        bl = true;
                    }
                } catch (CrashException e) {
                    exception(e, e.getReport(), false);
                } catch (Throwable e) {
                    exception(e, new CrashReport("Unexpected error", e), true);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            MinecraftClient.printCrashReport(this.crashReport);
        }
    }

    @Shadow
    public abstract void cleanUpAfterCrash();

    private void exception(Throwable e, CrashReport report, boolean unexpected) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            MinecraftClient.getInstance().getNetworkHandler().getConnection().disconnect(new LiteralText("Crashed"));
        }
        MinecraftClient.getInstance().disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
        customPrintCrashReport(report);
        ExceptionHandler.handleCrash(e, report, unexpected);
    }

    private void customPrintCrashReport(CrashReport crashReport) {
        File file = new File(MinecraftClient.getInstance().runDirectory, "crash-reports");
        File file2 = new File(file, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
        net.minecraft.Bootstrap.println(crashReport.asString());
        if (crashReport.getFile() != null) {
            net.minecraft.Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReport.getFile());
        } else if (crashReport.writeToFile(file2)) {
            net.minecraft.Bootstrap.println("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
        } else {
            net.minecraft.Bootstrap.println("#@?@# Game crashed! Crash report could not be saved. #@?@#");
        }

    }

}
