package me.deftware.client.framework.wrappers;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.mixin.imp.IMixinGuiEditSign;
import me.deftware.mixin.imp.IMixinSignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.text.LiteralText;

/**
 * Allows direct access to modify data in classes
 */
public class IClassHandler {

    /**
     * Returns a instance of a given IClass subclass
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
            return screen != null && screen.getClass() == clazz;
        }

    }

    /*
     * Classes
     */

    public static class IGuiEditSign extends IClass {

        public IGuiEditSign() {
            super(SignEditScreen.class);
        }

        public int getCurrentLine() {
            return ((IMixinGuiEditSign) screen).getEditLine();
        }

        public ChatMessage getText(int line) {
            return new ChatMessage().fromText(((IMixinSignBlockEntity) ((IMixinGuiEditSign) screen).getTileSign()).getTextRows()[line]);
        }

        public void setText(String text, int line) {
            ((IMixinGuiEditSign) screen).setTextOnLine(text, line);
            ((IMixinGuiEditSign) screen).getTileSign().setTextOnRow(line, new LiteralText(text));
        }

        public void save() {
            ((IMixinGuiEditSign) screen).getTileSign().markDirty();
        }

    }

}
