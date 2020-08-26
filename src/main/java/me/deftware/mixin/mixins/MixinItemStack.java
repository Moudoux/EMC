package me.deftware.mixin.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public class MixinItemStack {
    @Redirect(method = "addEnchantment", at = @At(value = "INVOKE", target = "net/minecraft/nbt/CompoundTag.putShort(Ljava/lang/String;S)V"))
    public void putShort(CompoundTag compoundTag, String key, short value, Enchantment enchantment, int level)
    {
        compoundTag.putShort(key, (short)level);
    }
}
