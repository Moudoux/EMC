package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(GuiScreen.class)
public class MixinGuiScreen implements IMixinGuiScreen {

	@Shadow
	private List<GuiButton> buttonList;

	@Shadow
	protected FontRenderer fontRenderer;

	@Override
	public List<GuiButton> getButtonList() {
		return buttonList;
	}

	@Override
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}

}
