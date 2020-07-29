package me.deftware.mixin.mixins;

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
    private String[] field_24285;

    @Override
    public int getEditLine() {
        return currentRow;
    }

    @Override
    public SignBlockEntity getTileSign() {
        return sign;
    }

    @Override
    public void setTextOnLine(String text, int line) {
        field_24285[line] = text;
    }

}
