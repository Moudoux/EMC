package me.deftware.mixin.mixins;

import me.deftware.mixin.imp.IMixinItemTool;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ToolItem.class)
public class MixinItemTool implements IMixinItemTool {

    /* TODO: Removed?
    @Shadow
    protected float attackDamage;
    */

    @Override
    public float getAttackDamage() {
        return 1;
    }

}
