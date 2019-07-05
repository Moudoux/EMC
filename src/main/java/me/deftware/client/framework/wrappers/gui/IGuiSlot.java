package me.deftware.client.framework.wrappers.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

@SuppressWarnings("all")
public abstract class IGuiSlot extends AlwaysSelectedEntryListWidget implements CustomIGuiEventListener {

    public IGuiSlot(int width, int height, int topIn, int bottomIn, int slotHeightIn) {
        super(MinecraftClient.getInstance(), width, height, topIn, bottomIn, slotHeightIn);
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        super.render(int_1, int_2, float_1);
        if (children().size() != getISize()) {
            buildItems();
        }
    }

    public int getSelectedSlot() {
        if (getSelected() == null) {
            return -1;
        }
        return ((CustomItem) getSelected()).id;
    }

    public void doDraw(int mouseX, int mouseY, float partialTicks) {
        render(mouseX, mouseY, partialTicks);
    }

    public void clickElement(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        if (children().size() + 1 > slotIndex && slotIndex >= 0) {
            setSelected((net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget.Entry) children().get(slotIndex));
        }
    }

    public void buildItems() {
        IGuiSlot.this.setSelected(null);
        children().clear();
        for (int i = 0; i < getISize(); i++) {
            addEntry(new CustomItem(i) {
                @Override
                public void render(int x, int y, int io, int i3, int i4, int i5, int i6, boolean b, float v) {
                    if (getISize() == 0) {
                        return;
                    }
                    if (id == getISize()) {
                        return;
                    }
                    drawISlot(id, (IGuiSlot.this.width / 2) - 105, y);
                }

                @Override
                public boolean mouseClicked(double double_1, double double_2, int int_1) {
                    if (int_1 == 0) {
                        IGuiSlot.this.setSelected(this);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    public abstract class CustomItem extends net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget.Entry {

        protected int id;

        public CustomItem(int id) {
            this.id = id;
        }

    }

    protected abstract int getISize();

    protected abstract void drawISlot(int id, int x, int y);

}
