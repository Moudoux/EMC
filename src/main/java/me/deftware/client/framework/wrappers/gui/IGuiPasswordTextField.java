package me.deftware.client.framework.wrappers.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;

@SuppressWarnings("All")
public class IGuiPasswordTextField extends IGuiTextField {

    private int enabledColor = 14737632;
    private int disabledColor = 7368816;

    public IGuiPasswordTextField(int id, int x, int y, int width, int height) {
        super(0, x, y, width, height);
    }

    @Override
    public void render(int p_195608_1_, int p_195608_2_, float p_195608_3_) {
        if (isVisible()) {
            if (isFocused()) {
                DrawableHelper.fill(((IMixinGuiTextField) this).getX() - 1, ((IMixinGuiTextField) this).getY() - 1, ((IMixinGuiTextField) this).getX() + ((IMixinGuiTextField) this).getWidth() + 1,
                        ((IMixinGuiTextField) this).getY() + ((IMixinGuiTextField) this).getHeight() + 1, -6250336);
                DrawableHelper.fill(((IMixinGuiTextField) this).getX(), ((IMixinGuiTextField) this).getY(), ((IMixinGuiTextField) this).getX() + ((IMixinGuiTextField) this).getWidth(), ((IMixinGuiTextField) this).getY() + ((IMixinGuiTextField) this).getHeight(),
                        -16777216);
            }
            int var1 = isFocused() ? enabledColor : disabledColor;
            int var2 = getCursor() - ((IMixinGuiTextField) this).getLineScrollOffset();
            int var3 = ((IMixinGuiTextField) this).getSelectionEnd() - ((IMixinGuiTextField) this).getLineScrollOffset();
            String text = getText().substring(((IMixinGuiTextField) this).getLineScrollOffset());
            String hidden = "";
            for (int i = 0; i < text.length(); i++) {
                hidden = hidden + "*";
            }
            String var4 = ((IMixinGuiTextField) this).getFontRendererInstance().wrapStringToWidth(hidden, ((IMixinGuiTextField) this).getWidth());
            boolean var5 = (var2 >= 0) && (var2 <= var4.length());
            boolean var6 = (isFocused()) && (((IMixinGuiTextField) this).getCursorCounter() / 6 % 2 == 0) && (var5);
            int var7 = isFocused() ? ((IMixinGuiTextField) this).getX() + 4 : ((IMixinGuiTextField) this).getX();
            int var8 = isFocused() ? ((IMixinGuiTextField) this).getY() + (((IMixinGuiTextField) this).getHeight() - 8) / 2 : ((IMixinGuiTextField) this).getY();
            int var9 = var7;
            if (var3 > var4.length()) {
                var3 = var4.length();
            }
            if (var4.length() > 0) {
                String var10 = var5 ? var4.substring(0, var2) : var4;
                var9 = ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(var10, var7, var8, var1);
            }
            boolean var13 = (getCursor() < getText().length()) || (getText().length() >= ((IMixinGuiTextField) this).getMaxTextLength());
            int var11 = var9;
            if (!var5) {
                var11 = var2 > 0 ? var7 + ((IMixinGuiTextField) this).getWidth() : var7;
            } else if (var13) {
                var11 = var9 - 1;
                var9--;
            }
            if ((var4.length() > 0) && (var5) && (var2 < var4.length())) {
                var9 = ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(var4.substring(var2), var9, var8, var1);
            }
            if (var6) {
                if (var13) {
                    DrawableHelper.fill(var11, var8 - 1, var11 + 1, var8 + 1 + ((IMixinGuiTextField) this).getFontRendererInstance().fontHeight,
                            -3092272);
                } else {
                    ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow("_", var11, var8, var1);
                }
            }
            if (var3 != var2) {
                int var12 = var7 + ((IMixinGuiTextField) this).getFontRendererInstance().getStringWidth(var4.substring(0, var3));
                drawCursorVertical(var11, var8 - 1, var12 - 1, var8 + 1 + ((IMixinGuiTextField) this).getFontRendererInstance().fontHeight);
            }
        }
    }

    protected void drawCursorVertical(int startX, int startY, int endX, int endY) {
        if (startX < endX) {
            int i = startX;
            startX = endX;
            endX = i;
        }

        if (startY < endY) {
            int j = startY;
            startY = endY;
            endY = j;
        }

        if (endX > ((IMixinGuiTextField) this).getX() + ((IMixinGuiTextField) this).getWidth()) {
            endX = ((IMixinGuiTextField) this).getX() + ((IMixinGuiTextField) this).getWidth();
        }

        if (startX > ((IMixinGuiTextField) this).getX() + ((IMixinGuiTextField) this).getWidth()) {
            startX = ((IMixinGuiTextField) this).getX() + ((IMixinGuiTextField) this).getWidth();
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBufferBuilder();
        GlStateManager.color4f(0.0F, 0.0F, 255.0F, 255.0F);
        GlStateManager.disableTexture();
        GlStateManager.enableColorLogicOp();
        GlStateManager.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.begin(7, VertexFormats.POSITION);
        bufferbuilder.vertex((double) startX, (double) endY, 0.0D).end();
        bufferbuilder.vertex((double) endX, (double) endY, 0.0D).end();
        bufferbuilder.vertex((double) endX, (double) startY, 0.0D).end();
        bufferbuilder.vertex((double) startX, (double) startY, 0.0D).end();
        tessellator.draw();
        GlStateManager.disableColorLogicOp();
        GlStateManager.enableTexture();
    }

}
