package me.deftware.client.framework.Wrappers.Item;

import me.deftware.client.framework.Wrappers.IBlock;
import me.deftware.client.framework.Wrappers.Item.Items.IItemArmor;
import me.deftware.client.framework.Wrappers.Objects.IBlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;

public class IItemStack {

	private ItemStack stack;

	public IItemStack(ItemStack stack) {
		this.stack = stack;
	}

	public IItemStack(IBlock block) {
		this.stack = new ItemStack(Item.getItemFromBlock(block.getBlock()));
	}

	public IItemStack(IItem item) {
		this.stack = new ItemStack(item.getItem());
	}

	public IItemStack(IItem item, int amount, int metadata) {
		this.stack = new ItemStack(item.getItem(), amount, metadata);
	}

	public IItemStack(String name) {
		this.stack = new ItemStack(Item.getByNameOrId(name));
	}

	public void setNBT(String nbt) throws Exception {
		this.stack.setTagCompound(JsonToNBT.getTagFromJson(nbt));
	}

	public void enchantAll() {
		for (Enchantment enchantment : Enchantment.REGISTRY) {
			try {
				if (enchantment != Enchantments.SILK_TOUCH && enchantment != Enchantments.field_190941_k
						&& enchantment != Enchantments.field_190940_C) {
					stack.addEnchantment(enchantment, 127);
				}
			} catch (Exception ex) {
				;
			}
		}
	}

	public void setStackDisplayName(String name) {
		this.stack.setStackDisplayName(name);
	}

	public static boolean validName(String name) {
		return Item.getByNameOrId(name) != null;
	}

	public ItemStack getStack() {
		return stack;
	}

	public String getDisplayName() {
		return stack.getDisplayName();
	}

	public int getItemID() {
		return Item.getIdFromItem(this.stack.getItem());
	}

	public float getStrVsBlock(IBlockPos pos) {
		return stack.getStrVsBlock(Minecraft.getMinecraft().world.getBlockState(pos.getPos()));
	}

	public boolean isEmpty() {
		return this.stack.getItem() == Item.getItemFromBlock(Blocks.AIR);
	}

	public static IItemStack cloneWithoutEffects(IItemStack stack) {
		return new IItemStack(new ItemStack(Item.getItemById(Item.getIdFromItem(stack.getStack().getItem())),
				stack.getStack().stackSize));
	}

	public IItem getIItem() {
		if (stack.getItem() instanceof ItemArmor) {
			return new IItemArmor(stack.getItem());
		}
		return new IItem(stack.getItem());
	}

	public int getRarity() {
		if (stack.getRarity() == EnumRarity.COMMON) {
			return 0;
		} else if (stack.getRarity() == EnumRarity.UNCOMMON) {
			return 1;
		} else if (stack.getRarity() == EnumRarity.RARE) {
			return 2;
		} else if (stack.getRarity() == EnumRarity.EPIC) {
			return 3;
		}
		return 0;
	}

	public boolean isArmor() {
		if (this.stack.getItem() instanceof ItemArmor) {
			return true;
		}
		return false;
	}

	public boolean isBow() {
		if (this.stack.getItem() instanceof ItemBow) {
			return true;
		}
		return false;
	}

}
