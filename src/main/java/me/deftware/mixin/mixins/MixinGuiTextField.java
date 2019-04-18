package me.deftware.mixin.mixins;

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

@Mixin(TextFieldWidget.class)
public abstract class MixinGuiTextField extends AbstractButtonWidget implements IMixinGuiTextField {

    public MixinGuiTextField(int int_1, int int_2, String string_1) {
        super(int_1, int_2, string_1);
    }

    private boolean useMinecraftScaling = true;
    private boolean useCustomFont = false;
    private EMCFont customFont;

    @Shadow
    private int maxLength;

    @Shadow
    private int focusedTicks;

    @Shadow
    private int cursorMin;

    @Shadow
    private int field_2103;

    @Shadow
    @Final
    private TextRenderer textRenderer;


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
        return cursorMin;
    }

    @Override
    public int getLineScrollOffset() {
        return field_2103;
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

    @Inject(method = "render", at = @At("HEAD"), remap = false)
    public void drawTextField(int p_drawTextField_1_, int p_drawTextField_2_, float p_drawTextField_3_, CallbackInfo ci) {
        if (!useMinecraftScaling) {
            GL11.glPushMatrix();
            GraphicsUtil.prepareMatrix(IGuiScreen.getDisplayWidth(), IGuiScreen.getDisplayHeight());
        }
    }

    @Inject(method = "render", at = @At("RETURN"), remap = false)
    public void drawTextFieldReturn(int p_drawTextField_1_, int p_drawTextField_2_, float p_drawTextField_3_, CallbackInfo ci) {
        if (!useMinecraftScaling) {
            GL11.glPopMatrix();
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "net/minecraft/client/font/TextRenderer.drawWithShadow(Ljava/lang/String;FFI)I"), remap = false)
    public int render(TextRenderer self, String text, float x, float y, int color) {
        if (useCustomFont) {
            customFont.drawStringWithShadow((int) x, (int) y - 6, text, new Color(color));
            return (int) (x + customFont.getStringWidth(text) + 1f);
        } else {
            return this.textRenderer.drawWithShadow(text, x, y, color);
        }
    }

    public int getMaxTextLength() {
        return maxLength;
    }


}
