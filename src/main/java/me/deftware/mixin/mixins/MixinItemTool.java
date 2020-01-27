package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinItemTool;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ToolItem.class)
public class MixinItemTool implements IMixinItemTool {

    @Final
    @Shadow
    private ToolMaterial material;

    @Override
    public float getAttackDamage() {
        return material.getAttackDamage();
    }

}
