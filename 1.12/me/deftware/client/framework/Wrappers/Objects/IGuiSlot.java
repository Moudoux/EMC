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
	protected void func_192637_a(int p_192637_1_, int p_192637_2_, int p_192637_3_, int p_192637_4_, int p_192637_5_,
			int p_192637_6_, float p_192637_7_) {
		this.drawISlot(p_192637_1_, p_192637_2_, p_192637_3_);
	}

	/*
	 * Wrappers
	 */

	protected abstract int getISize();

	protected abstract void drawISlot(int id, int x, int y);


}
