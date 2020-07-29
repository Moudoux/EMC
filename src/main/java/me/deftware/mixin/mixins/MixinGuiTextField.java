package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventChatboxType;
import me.deftware.client.framework.fonts.EMCFont;
import me.deftware.client.framework.utils.render.GraphicsUtil;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.lang.ref.WeakReference;
import java.util.function.BiFunction;

@Mixin(TextFieldWidget.class)
public abstract class MixinGuiTextField extends AbstractButtonWidget implements IMixinGuiTextField {

    public MixinGuiTextField(int int_1, int int_2, String string_1) {
        super(int_1, int_2, string_1);
    }

    private boolean useMinecraftScaling = true;
    private boolean useCustomFont = false;
    private EMCFont customFont;

    private boolean overlay = false;
    private String overlayText = "";

    @Shadow
    private int maxLength;

    @Shadow
    private int focusedTicks;

    @Shadow
    private String suggestion;

    @Shadow
    private boolean focused;

    @Shadow
    private int selectionEnd;

    @Shadow
    private int selectionStart;

    @Shadow
    private int firstCharacterIndex;

    @Shadow
    @Final
    private TextRenderer textRenderer;

    @Shadow
    private BiFunction<String, Integer, String> renderTextProvider;

    @Shadow
    private boolean editable;

    @Shadow public abstract String getText();

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public TextRenderer getFontRendererInstance() {
        return textRenderer;
    }

    @Override
    public int getCursorCounter() {
        return focusedTicks;
    }

    @Override
    public int getSelectionEnd() {
        return selectionEnd;
    }

    @Override
    public int getLineScrollOffset() {
        return firstCharacterIndex;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setUseMinecraftScaling(boolean state) {
        useMinecraftScaling = state;
    }

    @Override
    public void setUseCustomFont(boolean state) {
        useCustomFont = state;
    }

    @Override
    public void setCustomFont(EMCFont font) {
        customFont = font;
    }

    @Inject(method = "renderButton", at = @At("HEAD"))
    public void drawTextField(int p_drawTextField_1_, int p_drawTextField_2_, float p_drawTextField_3_, CallbackInfo ci) {
        if (!useMinecraftScaling) {
            GL11.glPushMatrix();
            GraphicsUtil.prepareMatrix(IGuiScreen.getDisplayWidth(), IGuiScreen.getDisplayHeight());
        }
    }

    @Inject(method = "renderButton", at = @At("RETURN"))
    public void drawTextFieldReturn(int p_drawTextField_1_, int p_drawTextField_2_, float p_drawTextField_3_, CallbackInfo ci) {
        if (!useMinecraftScaling) {
            GL11.glPopMatrix();
        }
        if (overlay) {
            String currentText = getText();
            int currentWidth = ((IMixinGuiTextField) this).getFontRendererInstance().getStringWidth(currentText);
            int x = isFocused() ? ((IMixinGuiTextField) this).getX() + 4 : ((IMixinGuiTextField) this).getX();
            int y = isFocused() ? ((IMixinGuiTextField) this).getY() + (((IMixinGuiTextField) this).getHeight() - 8) / 2 : ((IMixinGuiTextField) this).getY();
            ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(overlayText, x + currentWidth - 3, y - 2, Color.GRAY.getRGB());
            WeakReference<EventChatboxType> event = new WeakReference<>(new EventChatboxType(getText(), overlayText));
            event.get().broadcast();
            overlayText = event.get().getOverlay();
        }
    }

    @Redirect(method = "renderButton", at = @At(value = "INVOKE", target = "net/minecraft/client/font/TextRenderer.drawWithShadow(Ljava/lang/String;FFI)I"))
    public int render(TextRenderer self, String text, float x, float y, int color) {
        if (useCustomFont) {
            customFont.drawString((int) x, (int) y - 6, text, new Color(color), true);
            return (int) (x + customFont.getPrimitiveWidth(text) + 1f);
        } else {
            return this.textRenderer.drawWithShadow(text, x, y, color);
        }
    }

    public int getMaxTextLength() {
        return maxLength;
    }

    @Override
    public boolean getHasBorder() {
        return focused;
    }

    @Override
    public boolean getIsEditble() {
        return editable;
    }

    @Override
    public BiFunction<String, Integer, String> getRenderTextProvider() {
        return renderTextProvider;
    }

    @Override
    public String getSuggestion() {
        return suggestion;
    }

    @Override
    public int getCursorMax() {
        return selectionStart;
    }

    @Override
    public void setOverlay(boolean flag) {
        overlay = flag;
    }

}
