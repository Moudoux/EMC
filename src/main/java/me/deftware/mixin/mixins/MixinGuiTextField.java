package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiTextField.class)
public class MixinGuiTextField implements IMixinGuiTextField {

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
}
