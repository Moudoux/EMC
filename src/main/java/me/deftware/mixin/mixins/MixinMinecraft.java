package me.deftware.mixin.mixins;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.deftware.client.framework.event.events.EventGuiScreenDisplay;
import me.deftware.client.framework.event.events.EventKeyPress;
import me.deftware.client.framework.event.events.EventLeftClick;
import me.deftware.client.framework.event.events.EventMiddleClick;
import me.deftware.client.framework.event.events.EventSetFPS;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;

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
	private GameSettings gameSettings;

	@Shadow
	private WorldClient world;

	@Shadow
	private int rightClickDelayTimer;

	@Override
	@Shadow
	public abstract void displayGuiScreen(@Nullable GuiScreen guiScreenIn);

	@Shadow
	public abstract void rightClickMouse();

	@Shadow
	public abstract void clickMouse();

	@ModifyVariable(method = "displayGuiScreen", at = @At("HEAD"))
	private GuiScreen displayGuiScreenModifier(GuiScreen screen) {
		EventGuiScreenDisplay event = new EventGuiScreenDisplay(screen).send();
		return event.isCanceled() ? null : event.getScreen();
	}

	@Inject(method = "runTick", at = @At("HEAD"))
	private void runTick(CallbackInfo cb) {
		if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
			EventGuiScreenDisplay event = new EventGuiScreenDisplay(Minecraft.getMinecraft().currentScreen).send();
			if (!(event.getScreen() instanceof GuiMainMenu)) {
				displayGuiScreen(event.getScreen());
			}
		}
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

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public int getLimitFramerate() {
		int fps = world == null && currentScreen != null ? 30 : gameSettings.limitFramerate;
		EventSetFPS event = new EventSetFPS(fps, Display.isActive()).send();
		return event.getFps();
	}

	@Inject(method = "runTickMouse", at = @At(value = "INVOKE_ASSIGN", target = "org/lwjgl/input/Mouse.getEventButton()I", remap = false))
	private void onMouseEvent(CallbackInfo info) {
		if (currentScreen != null) {
			return;
		}
		int button = Mouse.getEventButton();
		if (button == 0) {
			new EventLeftClick().send();
		} else if (button == 1) {
			// Right click
		} else if (button == 2) {
			new EventMiddleClick().send();
		}
	}

	@Inject(method = "runTickKeyboard", at = @At(value = "INVOKE_ASSIGN", target = "org/lwjgl/input/Keyboard.getEventKeyState()Z", remap = false))
	private void onKeyEvent(CallbackInfo ci) {
		if (currentScreen != null) {
			return;
		}
		boolean down = Keyboard.getEventKeyState();
		if (down) {
			new EventKeyPress(Keyboard.getEventKey()).send();
		}
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

}
