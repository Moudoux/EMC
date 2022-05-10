package me.deftware.mixin.mixins;

import me.deftware.client.framework.fonts.EMCFont;
import me.deftware.client.framework.utils.render.GraphicsUtil;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(GuiTextField.class)
public class MixinGuiTextField implements IMixinGuiTextField {

	private boolean useMinecraftScaling = true;
	private boolean useCustomFont = false;
	private EMCFont customFont;

	@Final
	@Shadow
	private int height;

	@Shadow
	private int cursorCounter;

	@Shadow
	private int selectionEnd;

	@Shadow
	private int lineScrollOffset;

	@Shadow
	@Final
	private FontRenderer fontRenderer;

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public FontRenderer getFontRendererInstance() {
		return fontRenderer;
	}

	@Override
	public int getCursorCounter() {
		return cursorCounter;
	}

	@Override
	public int getSelectionEnd() {
		return selectionEnd;
	}

	@Override
	public int getLineScrollOffset() {
		return lineScrollOffset;
	}

	@Override
	public void setUseMinecraftScaling(boolean state) {
		useMinecraftScaling = state;
	}

	@Override
	public void setUseCustomFont(boolean state) {
		useCustomFont = state;
	}

	@Override
	public void setCustomFont(EMCFont font) {
		customFont = font;
	}


	@Inject(method = "drawTextField", at = @At("HEAD"))
	public void drawTextField(int p_drawTextField_1_, int p_drawTextField_2_, float p_drawTextField_3_, CallbackInfo ci) {
		if (!useMinecraftScaling) {
			GL11.glPushMatrix();
			GraphicsUtil.prepareMatrix(IGuiScreen.getDisplayWidth(), IGuiScreen.getDisplayHeight());
		}
	}

	@Inject(method = "drawTextField", at = @At("RETURN"))
	public void drawTextFieldReturn(int p_drawTextField_1_, int p_drawTextField_2_, float p_drawTextField_3_, CallbackInfo ci) {
		if (!useMinecraftScaling) {
			GL11.glPopMatrix();
		}
	}

	@Redirect(method = "drawTextField", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/FontRenderer.drawStringWithShadow(Ljava/lang/String;FFI)I"))
	public int onDrawText(FontRenderer self, String text, float x, float y, int color) {
		if (useCustomFont) {
			customFont.drawStringWithShadow((int) x, (int) y - 6, text, new Color(color));
			return (int) (x + customFont.getStringWidth(text) + 1f);
		} else {
			return this.fontRenderer.drawStringWithShadow(text, x, y, color);
		}
	}

}
