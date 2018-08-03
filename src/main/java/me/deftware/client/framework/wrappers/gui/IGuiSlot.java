package me.deftware.client.framework.wrappers.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import org.lwjgl.glfw.GLFW;

public abstract class IGuiSlot extends GuiSlot {

	private int selectedSlot;

	public IGuiSlot(int width, int height, int topIn, int bottomIn, int slotHeightIn) {
		super(Minecraft.getMinecraft(), width, height, topIn, bottomIn, slotHeightIn);
	}

	@Override
	protected int getSize() {
		return getISize();
	}

	@Override
	protected boolean func_195078_a(int index, int p_195078_2_, double p_195078_3_, double p_195078_5_) {
		selectedSlot = index;
		return true;
	}

	@Override
	protected boolean isSelected(int slotIndex) {
		return selectedSlot == slotIndex;
	}

	@Override
	protected void drawSlot(int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks) {
		drawISlot(slotIndex, xPos, yPos);
	}

	protected abstract int getISize();

	protected abstract void drawISlot(int id, int x, int y);

	public int getSelectedSlot() {
		return selectedSlot;
	}

	public void doDraw(int mouseX, int mouseY, float partialTicks) {
		drawScreen(mouseX, mouseY, partialTicks);
	}

	public void clickElement(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
		func_195078_a(slotIndex, GLFW.GLFW_MOUSE_BUTTON_LEFT, mouseX, mouseY);
	}

}
