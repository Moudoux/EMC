package me.deftware.mixin.imp;

import net.minecraft.block.entity.SignBlockEntity;

public interface IMixinGuiEditSign {

    int getEditLine();

    SignBlockEntity getTileSign();

    void setTextOnLine(String text, int line);

}
