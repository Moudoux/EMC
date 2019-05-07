package me.deftware.client.framework.wrappers;

import me.deftware.mixin.imp.IMixinGuiEditSign;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.ingame.EditSignScreen;
import net.minecraft.network.chat.TextComponent;

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

        protected Screen screen = MinecraftClient.getInstance().currentScreen;
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
            super(EditSignScreen.class);
        }

        public int getCurrentLine() {
            return ((IMixinGuiEditSign) screen).getEditLine();
        }

        public String getText(int line) {
            return ((IMixinGuiEditSign) screen).getTileSign().text[line].getText();
        }

        public void setText(String text, int line) {
            ((IMixinGuiEditSign) screen).getTileSign().text[line] = new TextComponent(text);
        }

        public void save() {
            ((IMixinGuiEditSign) screen).getTileSign().markDirty();
        }

    }

}
