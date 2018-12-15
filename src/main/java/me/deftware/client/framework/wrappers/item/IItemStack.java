package me.deftware.client.framework.wrappers.item;

import me.deftware.client.framework.wrappers.item.items.IItemArmor;
import me.deftware.client.framework.wrappers.world.IBlock;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class IItemStack {

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
		stack = new ItemStack(IItem.getByNameOrId(name));
	}

	public IItemStack(String name, int amount) {
		stack = new ItemStack(IItem.getByNameOrId(name), amount);
	}

	public void setNBT(String nbt) throws Exception {
		stack.setTag(JsonToNBT.getTagFromJson(nbt));
	}

	public void enchantAll(int level) {
		for (Object enchantment : IRegistry.ENCHANTMENT) {
			if (enchantment != Enchantments.SILK_TOUCH && enchantment != Enchantments.BINDING_CURSE
					&& enchantment != Enchantments.VANISHING_CURSE) {
				stack.addEnchantment((Enchantment) enchantment, level);
			}
		}
	}

	public static IItemStack cloneWithoutEffects(IItemStack stack) {
		return new IItemStack(new ItemStack(Item.getItemById(Item.getIdFromItem(stack.getStack().getItem())),
				Integer.valueOf(stack.getStack().toString().split("x")[0])));
	}

	public void setStackDisplayName(String name) {
		NBTTagCompound nbttagcompound = stack.getOrCreateChildTag("display");
		nbttagcompound.putString("Name", ITextComponent.Serializer.toJson(new TextComponentString(name)));
	}

	public static boolean validName(String name) {
		return IItem.getByNameOrId(name) != null;
	}

	public ItemStack getStack() {
		return stack;
	}

	public String getDisplayName() {
		return stack.getDisplayName().getUnformattedComponentText();
	}

	public int getItemID() {
		return Item.getIdFromItem(stack.getItem());
	}

	public float getStrVsBlock(IBlockPos pos) {
		return stack.getDestroySpeed(Minecraft.getInstance().world.getBlockState(pos.getPos()));
	}

	public boolean isEmpty() {
		return stack.getItem() == Item.getItemFromBlock(Blocks.AIR);
	}

	public IItem getIItem() {
		if (stack.getItem() instanceof ItemArmor) {
			return new IItemArmor(stack.getItem());
		}
		return new IItem(stack.getItem());
	}

	public boolean hasEffect(IEffects ieffect) {
		for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
			if (effect.getPotion() == ieffect.getEffect()) {
				return true;
			}
		}
		return false;
	}

	public enum IEffects {
		InstantHelth(MobEffects.INSTANT_HEALTH);

		private Potion effect;

		IEffects(Potion effect) {
			this.effect = effect;
		}

		public Potion getEffect() {
			return effect;
		}
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
		if (stack.getItem() instanceof ItemArmor) {
			return true;
		}
		return false;
	}

	public boolean isBow() {
		if (stack.getItem() instanceof ItemBow) {
			return true;
		}
		return false;
	}

}