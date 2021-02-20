package me.deftware.mixin.mixins.block;

import me.deftware.mixin.imp.IMixinAbstractBlock;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractBlock.class)
public abstract class MixinAbstractBlock implements IMixinAbstractBlock {

    @Shadow @Final
    protected float slipperiness;

    @Shadow @Final
    protected float velocityMultiplier;

    @Override
    public float getTheSlipperiness() {
        return this.slipperiness;
    }

    @Override
    public float getTheVelocityMultiplier() {
        return this.velocityMultiplier;
    }

}
