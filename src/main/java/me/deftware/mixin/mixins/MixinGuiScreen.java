package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IGuiEventListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(GuiScreen.class)
public class MixinGuiScreen implements IMixinGuiScreen {

	@Shadow
	private List<GuiButton> buttonList;

	@Shadow
	@Final
	private List<IGuiEventListener> field_195124_j;

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

	@Override
	public List<IGuiEventListener> getEventList() {
		return field_195124_j;
	}

}
