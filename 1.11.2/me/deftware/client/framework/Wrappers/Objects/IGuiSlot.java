package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;

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
	protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
		this.selectedSlot = slotIndex;
	}

	@Override
	protected boolean isSelected(int slotIndex) {
		return selectedSlot == slotIndex;
	}

	@Override
	protected void drawBackground() {

	}

	@Override
	/**
	 * drawSlot(int id, int x, int y, int var4, int var5, int var6)
	 */
	protected void drawSlot(int id, int x, int y, int var4, int var5, int var6) {
		this.drawISlot(id, x, y);
	}

	/*
	 * Handlers
	 */

	protected abstract int getISize();

	protected abstract void drawISlot(int id, int x, int y);

	/*
	 * Wrappers
	 */

	public int getSelectedSlot() {
		return selectedSlot;
	}

	public void onMouseInput() {
		this.handleMouseInput();
	}

	public void doDraw(int mouseX, int mouseY, float partialTicks) {
		this.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void registerScrollbars(int scrollUpButtonIDIn, int scrollDownButtonIDIn) {
		this.registerScrollButtons(scrollUpButtonIDIn, scrollDownButtonIDIn);
	}

	public void clickElement(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
		this.elementClicked(slotIndex, isDoubleClick, mouseX, mouseY);
	}

}
