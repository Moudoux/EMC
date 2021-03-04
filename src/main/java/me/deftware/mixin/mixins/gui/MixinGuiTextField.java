package me.deftware.mixin.mixins.gui;

import me.deftware.client.framework.event.events.EventChatboxType;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.lang.ref.WeakReference;
import java.util.function.BiFunction;

@Mixin(TextFieldWidget.class)
public abstract class MixinGuiTextField extends AbstractButtonWidget implements IMixinGuiTextField {

    public MixinGuiTextField(int x, int y, Text message) {
        super(x, y, 200, 20, message);
    }

    @Unique
    private boolean overlay = false, passwordField = false;

    @Unique
    private String overlayText = "";

    @Shadow
    private int maxLength;

    @Shadow
    private int focusedTicks;

    @Shadow
    private String suggestion;

    @Shadow
    private boolean focusUnlocked;

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
    private BiFunction<String, Integer, OrderedText> renderTextProvider;

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

    @Unique @Override
    public void setPasswordField(boolean flag) {
        this.passwordField = flag;
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "renderButton", at = @At("RETURN"))
    public void drawTextFieldReturn(MatrixStack matrixStack, int mouseX, int mouseY, float tickDelta, CallbackInfo ci) {
        if (overlay) {
            String currentText = getText();
            int currentWidth = ((IMixinGuiTextField) this).getFontRendererInstance().getWidth(currentText);
            int x = isFocused() ? ((IMixinGuiTextField) this).getX() + 4 : ((IMixinGuiTextField) this).getX();
            int y = isFocused() ? ((IMixinGuiTextField) this).getY() + (((IMixinGuiTextField) this).getHeight() - 8) / 2 : ((IMixinGuiTextField) this).getY();
            ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(matrixStack, overlayText, x + currentWidth - 3, y - 2, Color.GRAY.getRGB());
            WeakReference<EventChatboxType> event = new WeakReference<>(new EventChatboxType(getText(), overlayText));
            event.get().broadcast();
            overlayText = event.get().getOverlay();
        }
    }

    @Redirect(method = "renderButton", at = @At(value = "INVOKE", target = "Ljava/util/function/BiFunction;apply(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object render(BiFunction<String, Integer, OrderedText> biFunction, Object text, Object index) {
        String data = (String) text;
        if (passwordField) {
            StringBuilder hidden = new StringBuilder();
            for (int i = 0; i < data.length(); i++) {
                hidden.append("*");
            }
            data = hidden.toString();
        }
        return renderTextProvider.apply(data, (int) index);
    }

    public int getMaxTextLength() {
        return maxLength;
    }

    @Override
    public boolean getHasBorder() {
        return focusUnlocked;
    }

    @Override
    public boolean getIsEditable() {
        return editable;
    }

    @Override
    public BiFunction<String, Integer, OrderedText> getRenderTextProvider() {
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
