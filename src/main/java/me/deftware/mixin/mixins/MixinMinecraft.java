package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventGuiScreenDisplay;
import me.deftware.client.framework.event.events.EventShutdown;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMixinMinecraft {

	@Mutable
	@Shadow
	@Final
	private Session session;

	@Shadow
	@Final
	private Timer timer;

	@Shadow
	@Nullable
	private GuiScreen currentScreen;

	@Shadow
	private int rightClickDelayTimer;

	@Override
	@Shadow
	public abstract void displayGuiScreen(@Nullable GuiScreen guiScreenIn);

	@Shadow
	public abstract void rightClickMouse();

	@Shadow
	public abstract void clickMouse();

	@Shadow
	public abstract void middleClickMouse();

	@ModifyVariable(method = "displayGuiScreen", at = @At("HEAD"))
	private GuiScreen displayGuiScreenModifier(GuiScreen screen) {
		EventGuiScreenDisplay event = new EventGuiScreenDisplay(screen).send();
		return event.isCanceled() ? null : event.getScreen();
	}

	@Inject(method = "runTick", at = @At("HEAD"))
	private void runTick(CallbackInfo ci) {
		if (Minecraft.getInstance().currentScreen instanceof GuiMainMenu) {
			EventGuiScreenDisplay event = new EventGuiScreenDisplay(Minecraft.getInstance().currentScreen).send();
			if (!(event.getScreen() instanceof GuiMainMenu)) {
				displayGuiScreen(event.getScreen());
			}
		}
	}

	@Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
	public void shutdownMinecraftApplet(CallbackInfo ci) {
		new EventShutdown().send();
		Bootstrap.isRunning = false;
	}

	@Override
	public void setRightClickDelayTimer(int delay) {
		this.rightClickDelayTimer = delay;
	}

	@Override
	public void doClickMouse() {
		clickMouse();
	}

	@Override
	public void doRightClickMouse() {
		rightClickMouse();
	}

	@Override
	public void doMiddleClickMouse() {
		middleClickMouse();
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
	public Timer getTimer() {
		return timer;
	}

	@Override
	public MainWindow getMainWindow() {
		return Minecraft.getInstance().mainWindow;
	}

}
