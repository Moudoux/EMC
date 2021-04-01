package me.deftware.mixin.mixins.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public class MixinItemStack {

    @Redirect(method = "addEnchantment", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putShort(Ljava/lang/String;S)V"))
    public void putShort(net.minecraft.nbt.NbtCompound compoundTag, String key, short value, Enchantment enchantment, int level) {
        compoundTag.putShort(key, (short)level);
    }

}
