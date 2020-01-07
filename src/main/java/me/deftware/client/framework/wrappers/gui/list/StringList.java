package me.deftware.client.framework.wrappers.gui.list;

import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import me.deftware.client.framework.wrappers.render.IFontRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.EntryListWidget;

@SuppressWarnings("ALL")
public class StringList extends EntryListWidget {

	public StringList(int width, int height, int top, int bottom, int itemHeight) {
		super(MinecraftClient.getInstance(), width, height, top, bottom, itemHeight);
	}

	public void clear() {
		this.children().clear();
	}

	public void addItem(String text) {
		this.children().add(new StringEntry(text));
	}

	public void doDraw(int mouseX, int mouseY, float delta) {
		this.render(mouseX, mouseY, delta);
	}

	public void addToEventListener(IGuiScreen screen) {
		screen.addRawEventListener(this);
	}

	@Override
	public int getRowWidth() {
		return width - 2;
	}

	@Override
	protected int getScrollbarPosition() {
		return width - 6;
	}

	public static class StringEntry extends EntryListWidget.Entry {

		private String string;

		public StringEntry(String string) {
			this.string = string;
		}

		public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
			String render = string;
			if (IFontRenderer.getStringWidth(render) > width) {
				render = "";
				for (String s : string.split("")) {
					if (IFontRenderer.getStringWidth(render + s + "...") < width - 6) {
						render += s;
					} else {
						render += "...";
						break;
					}
				}
			}
			IFontRenderer.drawString(render, x, y, 0xFFFFFF);
		}

	}

}