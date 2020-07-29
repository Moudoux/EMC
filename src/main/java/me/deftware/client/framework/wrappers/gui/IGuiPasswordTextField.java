package me.deftware.client.framework.wrappers.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.deftware.mixin.imp.IMixinGuiTextField;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;

@SuppressWarnings("All")
public class IGuiPasswordTextField extends IGuiTextField {

    private int enabledColor = 14737632;
    private int disabledColor = 7368816;

    public IGuiPasswordTextField(int id, int x, int y, int width, int height) {
        super(0, x, y, width, height);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int int_1, int int_2, float float_1) {
        if (this.isVisible()) {
            if (((IMixinGuiTextField) this).getHasBorder()) {
                fill(matrixStack, this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, -6250336);
                fill(matrixStack, this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
            }

            int int_3 = ((IMixinGuiTextField) this).getIsEditble() ? this.enabledColor : this.disabledColor;
            int int_4 = ((IMixinGuiTextField) this).getCursorMax() - ((IMixinGuiTextField) this).getLineScrollOffset();
            int int_5 = ((IMixinGuiTextField) this).getSelectionEnd() - ((IMixinGuiTextField) this).getLineScrollOffset();

            // Replace chars
            String text = this.getText();
            StringBuilder hidden = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                hidden.append("*");
            }
            text = hidden.toString();

            String string_1 = ((IMixinGuiTextField) this).getFontRendererInstance().trimToWidth(text.substring(((IMixinGuiTextField) this).getLineScrollOffset()), this.getInnerWidth());
            boolean boolean_1 = int_4 >= 0 && int_4 <= string_1.length();
            boolean boolean_2 = this.isFocused() && ((IMixinGuiTextField) this).getCursorCounter() / 6 % 2 == 0 && boolean_1;
            int int_6 = ((IMixinGuiTextField) this).getHasBorder() ? this.x + 4 : this.x;
            int int_7 = ((IMixinGuiTextField) this).getHasBorder() ? this.y + (this.height - 8) / 2 : this.y;
            int int_8 = int_6;
            if (int_5 > string_1.length()) {
                int_5 = string_1.length();
            }

            if (!string_1.isEmpty()) {
                String string_2 = boolean_1 ? string_1.substring(0, int_4) : string_1;
                int_8 = ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(matrixStack, ((IMixinGuiTextField) this).getRenderTextProvider().apply(string_2, ((IMixinGuiTextField) this).getLineScrollOffset()), (float)int_6, (float)int_7, int_3);
            }

            boolean boolean_3 = ((IMixinGuiTextField) this).getCursorMax() < this.getText().length() || this.getText().length() >= ((IMixinGuiTextField) this).getMaxTextLength();
            int int_9 = int_8;
            if (!boolean_1) {
                int_9 = int_4 > 0 ? int_6 + this.width : int_6;
            } else if (boolean_3) {
                int_9 = int_8 - 1;
                --int_8;
            }

            if (!string_1.isEmpty() && boolean_1 && int_4 < string_1.length()) {
                ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(matrixStack, ((IMixinGuiTextField) this).getRenderTextProvider().apply(string_1.substring(int_4), ((IMixinGuiTextField) this).getCursorMax()), (float)int_8, (float)int_7, int_3);
            }

            if (!boolean_3 && ((IMixinGuiTextField) this).getSuggestion() != null) {
                ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(matrixStack, ((IMixinGuiTextField) this).getSuggestion(), (float)(int_9 - 1), (float)int_7, -8355712);
            }

            int var10002;
            int var10003;
            if (boolean_2) {
                if (boolean_3) {
                    int var10001 = int_7 - 1;
                    var10002 = int_9 + 1;
                    var10003 = int_7 + 1;
                    ((IMixinGuiTextField) this).getFontRendererInstance().getClass();
                    DrawableHelper.fill(matrixStack, int_9, var10001, var10002, var10003 + 9, -3092272);
                } else {
                    ((IMixinGuiTextField) this).getFontRendererInstance().drawWithShadow(matrixStack, "_", (float)int_9, (float)int_7, int_3);
                }
            }

            if (int_5 != int_4) {
                int int_10 = int_6 + ((IMixinGuiTextField) this).getFontRendererInstance().getWidth(string_1.substring(0, int_5));
                var10002 = int_7 - 1;
                var10003 = int_10 - 1;
                int var10004 = int_7 + 1;
                ((IMixinGuiTextField) this).getFontRendererInstance().getClass();
                this.method_1886(int_9, var10002, var10003, var10004 + 9);
            }

        }
    }

    private void method_1886(int int_1, int int_2, int int_3, int int_4) {
        int int_6;
        if (int_1 < int_3) {
            int_6 = int_1;
            int_1 = int_3;
            int_3 = int_6;
        }

        if (int_2 < int_4) {
            int_6 = int_2;
            int_2 = int_4;
            int_4 = int_6;
        }

        if (int_3 > this.x + this.width) {
            int_3 = this.x + this.width;
        }

        if (int_1 > this.x + this.width) {
            int_1 = this.x + this.width;
        }

        Tessellator tessellator_1 = Tessellator.getInstance();
        BufferBuilder bufferBuilder_1 = tessellator_1.getBuffer();
        RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferBuilder_1.begin(7, VertexFormats.POSITION);
        bufferBuilder_1.vertex(int_1, int_4, 0.0D).next();
        bufferBuilder_1.vertex(int_3, int_4, 0.0D).next();
        bufferBuilder_1.vertex(int_3, int_2, 0.0D).next();
        bufferBuilder_1.vertex(int_1, int_2, 0.0D).next();
        tessellator_1.draw();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

}
