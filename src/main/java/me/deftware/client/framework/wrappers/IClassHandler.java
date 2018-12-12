package me.deftware.client.framework.wrappers;

import me.deftware.mixin.imp.IMixinGuiEditSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.util.text.TextComponentString;

/**
 * Allows direct access to modify data in classes
 */
public class IClassHandler {

	/**
	 * Returns a instance of a given IClass subclass
	 *
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T extends IClass> T getClass(Class<T> clazz) throws Exception {
		return clazz.newInstance();
	}

	/**
	 * The superclass all subclasses must extend for the generic casting to work
	 */
	public static class IClass {

		protected GuiScreen screen = Minecraft.getInstance().currentScreen;
		protected Class<?> clazz;

		public IClass(Class<?> clazz) {
			this.clazz = clazz;
		}

		public boolean isInstance() {
			return screen.getClass() == clazz;
		}

	}

	/*
	 * Classes
	 */

	public static class IGuiEditSign extends IClass {

		public IGuiEditSign() {
			super(GuiEditSign.class);
		}

		public int getCurrentLine() {
			return ((IMixinGuiEditSign) screen).getEditLine();
		}

		public String getText(int line) {
			return ((IMixinGuiEditSign) screen).getTileSign().signText[line].getUnformattedComponentText();
		}

		public void setText(String text, int line) {
			((IMixinGuiEditSign) screen).getTileSign().signText[line] = new TextComponentString(text);
		}

		public void save() {
			((IMixinGuiEditSign) screen).getTileSign().markDirty();
		}

	}

}
