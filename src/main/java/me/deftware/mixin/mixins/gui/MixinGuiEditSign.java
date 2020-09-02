package me.deftware.mixin.mixins.gui;

import me.deftware.mixin.imp.IMixinGuiEditSign;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SignEditScreen.class)
public class MixinGuiEditSign implements IMixinGuiEditSign {

    @Final
    @Shadow
    private SignBlockEntity sign;

    @Shadow
    private int currentRow;

    @Shadow
    @Final
    private String[] text;

    @Override
    public int getEditLine() {
        return currentRow;
    }

    @Override
    public SignBlockEntity getTileSign() {
        return sign;
    }

    @Override
    public void setTextOnLine(String newText, int line) {
        text[line] = newText;
    }

}
