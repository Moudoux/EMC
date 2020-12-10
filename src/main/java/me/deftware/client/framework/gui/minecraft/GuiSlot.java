package me.deftware.client.framework.gui.minecraft;

import me.deftware.client.framework.gui.GuiEventListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

/**
 * @author Deftware
 */
public abstract class GuiSlot extends AlwaysSelectedEntryListWidget<GuiSlot.CustomItem> implements GuiEventListener {

	private static final MatrixStack stack = new MatrixStack();

	public GuiSlot(int width, int height, int topIn, int bottomIn, int slotHeightIn) {
		super(MinecraftClient.getInstance(), width, height, topIn, bottomIn, slotHeightIn);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float tickDelta) {
		super.render(matrixStack, mouseX, mouseY, tickDelta);
		if (children().size() != getISize()) {
			buildItems();
		}
	}

	@Override
	public int getRowWidth() {
		return getCustomRowWidth();
	}

	@Override
	protected int getScrollbarPositionX() {
		return getCustomScrollbarPositionX();
	}

	protected int getCustomRowWidth() {
		return 220;
	}

	protected int getCustomScrollbarPositionX() {
		return this.width / 2 + 124;
	}

	public int getSelectedSlot() {
		if (getSelected() == null) {
			return -1;
		}
		return getSelected().id;
	}

	public void doDraw(int mouseX, int mouseY, float partialTicks) {
		render(stack, mouseX, mouseY, partialTicks);
	}

	public void clickElement(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
		if (children().size() + 1 > slotIndex && slotIndex >= 0) {
			setSelected(children().get(slotIndex));
		}
	}

	public void buildItems() {
		GuiSlot.this.setSelected(null);
		children().clear();
		for (int index = 0; index < getISize(); index++) {
			addEntry(new CustomItem(index) {
				@Override
				public void render(MatrixStack matrixStack, int x, int y, int io, int i3, int i4, int i5, int i6, boolean b, float v) {
					if (getISize() == 0) {
						return;
					}
					if (id == getISize()) {
						return;
					}
					drawISlot(id, (GuiSlot.this.width / 2) - 105, y);
				}

				@Override
				public boolean mouseClicked(double mouseX, double mouseY, int button) {
					if (button == 0) {
						GuiSlot.this.setSelected(this);
						return true;
					} else {
						return false;
					}
				}
			});
		}
	}

	public abstract static class CustomItem extends AlwaysSelectedEntryListWidget.Entry<GuiSlot.CustomItem> {

		protected int id;

		public CustomItem(int id) {
			this.id = id;
		}

	}

	protected abstract int getISize();

	protected abstract void drawISlot(int id, int x, int y);

}
