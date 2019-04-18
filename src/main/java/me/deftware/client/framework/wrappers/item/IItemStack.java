package me.deftware.client.framework.wrappers.item;

import me.deftware.client.framework.wrappers.item.items.IItemArmor;
import me.deftware.client.framework.wrappers.world.IBlock;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class IItemStack {

    public static final IItemStack EMPTY = new IItemStack(ItemStack.EMPTY);

    private ItemStack stack;

    public IItemStack(ItemStack stack) {
        this.stack = stack;
    }

    public IItemStack(IBlock block) {
        stack = new ItemStack(Item.getItemFromBlock(block.getBlock()));
    }

    public IItemStack(IItem item) {
        stack = new ItemStack(item.getItem());
    }

    public IItemStack(IItem item, int amount) {
        stack = new ItemStack(item.getItem(), amount);
    }

    public IItemStack(String name) {
        stack = new ItemStack(IItem.getByName(name));
    }

    public IItemStack(String name, int amount) {
        stack = new ItemStack(IItem.getByName(name), amount);
    }

    public static IItemStack cloneWithoutEffects(IItemStack stack) {
        return new IItemStack(new ItemStack(Item.byRawId(Item.getRawIdByItem(stack.getStack().getItem())),
                Integer.valueOf(stack.getStack().toString().split("x")[0])));
    }

    public static boolean validName(String name) {
        return IItem.getByName(name) != null;
    }

    public void setNBT(String nbt) throws Exception {
        stack.setTag(StringNbtReader.parse(nbt));
    }

    public void enchantAll(int level) {
        for (Object enchantment : Registry.ENCHANTMENT) {
            if (enchantment != Enchantments.SILK_TOUCH && enchantment != Enchantments.BINDING_CURSE
                    && enchantment != Enchantments.VANISHING_CURSE) {
                stack.addEnchantment((Enchantment) enchantment, level);
            }
        }
    }

    public static IItemStack read(INBTTagCompound compound) {
        return new IItemStack(ItemStack.fromTag(compound.getCompound()));
    }

    public void setStackDisplayName(String name) {
        CompoundTag nbttagcompound = stack.getOrCreateSubCompoundTag("display");
        nbttagcompound.putString("Name", TextComponent.Serializer.toJson(new StringTextComponent(name)).toString());
    }

    public boolean hasCompoundTag() {
        return stack.hasTag();
    }

    public void setTagInfo(String key, INBTTagList compound) {
        stack.setChildTag(key, compound.list);
    }

    public int getMaxStackSize() {
        return stack.getMaxAmount();
    }

    public static boolean areItemStackTagsEqual(IItemStack one, IItemStack two) {
        return ItemStack.areEqualIgnoreTags(one.getStack(), two.getStack());
    }

    public boolean isItemEqual(IItemStack stack) {
        return this.stack.isEqualIgnoreTags(stack.getStack());
    }

    public void setCount(int count) {
        stack.setAmount(count);
    }

    public int getCount() {
        return stack.getAmount();
    }

    public INBTTagCompound getTagCompound() {
        return new INBTTagCompound(stack.getTag());
    }

    public ItemStack getStack() {
        return stack;
    }

    public String getDisplayName() {
        return stack.getDisplayName().getString();
    }

    public int getItemID() {
        return Item.getRawIdByItem(stack.getItem());
    }

    public float getStrVsBlock(IBlockPos pos) {
        return stack.getBlockBreakingSpeed(MinecraftClient.getInstance().world.getBlockState(pos.getPos()));
    }

    public boolean isEmpty() {
        return stack.getItem() == Item.getItemFromBlock(Blocks.AIR);
    }

    public IItem getIItem() {
        if (stack.getItem() instanceof ArmorItem) {
            return new IItemArmor(stack.getItem());
        }
        return new IItem(stack.getItem());
    }

    public boolean hasEffect(IEffects ieffect) {
        for (StatusEffectInstance effect : PotionUtil.getPotionEffects(stack)) {
            if (effect.getEffectType() == ieffect.getEffect()) {
                return true;
            }
        }
        return false;
    }

    public int getRarity() {
        if (stack.getRarity() == Rarity.COMMON) {
            return 0;
        } else if (stack.getRarity() == Rarity.UNCOMMON) {
            return 1;
        } else if (stack.getRarity() == Rarity.RARE) {
            return 2;
        } else if (stack.getRarity() == Rarity.EPIC) {
            return 3;
        }
        return 0;
    }

    public boolean isArmor() {
        if (stack.getItem() instanceof ArmorItem) {
            return true;
        }
        return false;
    }

    public boolean isBow() {
        if (stack.getItem() instanceof BowItem) {
            return true;
        }
        return false;
    }

    public int getEnchantmentLevel(int enchantID) {
        return EnchantmentHelper.getLevel(Enchantment.byRawId(enchantID), getStack());
    }

    public enum IEffects {

        InstantHelth(StatusEffects.INSTANT_HEALTH);

        private StatusEffect effect;

        IEffects(StatusEffect effect) {
            this.effect = effect;
        }

        public StatusEffect getEffect() {
            return effect;
        }

    }

}