package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinGuiEditSign;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.ingame.EditSignScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EditSignScreen.class)
public class MixinGuiEditSign implements IMixinGuiEditSign {

    @Final
    @Shadow
    private SignBlockEntity sign;

    @Shadow
    private int currentRow;

    @Override
    public int getEditLine() {
        return currentRow;
    }

    @Override
    public SignBlockEntity getTileSign() {
        return sign;
    }

}
