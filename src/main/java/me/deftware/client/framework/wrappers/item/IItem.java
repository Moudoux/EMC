package me.deftware.client.framework.wrappers.item;

import me.deftware.mixin.imp.IMixinItemTool;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

import javax.annotation.Nullable;

public class IItem {

	private Item item;

	public IItem(String name) {
		item = getByNameOrId(name);
	}

	public IItem(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public String getName() {
		return item.getName().getUnformattedComponentText();
	}

	public String getTranslationKey() {
		return item.getTranslationKey();
	}

	public int getID() {
		return Item.getIdFromItem(item);
	}

	public boolean isValidItem() {
		return item != null;
	}

	public float getAttackDamage() {
		return ((ItemSword) item).getAttackDamage() + 3.0F;
	}

	public float getDamageVsEntity() {
		return ((IMixinItemTool) item).getAttackDamage();
	}

	public boolean isThrowable() {
		if (item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg
				|| item instanceof ItemEnderPearl || item instanceof ItemSplashPotion
				|| item instanceof ItemLingeringPotion || item instanceof ItemFishingRod) {
			return true;
		}
		return false;
	}

	public boolean instanceOf(IItemType type) {
		if (type.equals(IItemType.ItemFishingRod)) {
			return item instanceof ItemFishingRod;
		} else if (type.equals(IItemType.ItemPotion)) {
			return item instanceof ItemPotion;
		} else if (type.equals(IItemType.SplashPotion)) {
			return item == Items.SPLASH_POTION;
		} else if (type.equals(IItemType.ItemFood)) {
			return item instanceof ItemFood;
		} else if (type.equals(IItemType.ItemSword)) {
			return item instanceof ItemSword;
		} else if (type.equals(IItemType.ItemTool)) {
			return item instanceof ItemTool;
		} else if (type.equals(IItemType.ItemNameTag)) {
			return item instanceof ItemNameTag;
		} else if (type.equals(IItemType.ItemBlock)) {
			return item instanceof ItemBlock;
		} else if (type.equals(IItemType.ItemSoup)) {
			return item instanceof ItemSoup;
		} else if (type.equals(IItemType.ItemHoe)) {
			return item instanceof ItemHoe;
		}
		return false;
	}

	public enum IItemType {
		ItemPotion, ItemFishingRod, ItemFood, ItemSword, ItemTool, ItemNameTag, ItemBlock, ItemHoe, SplashPotion,
		ItemSoup
	}

	@Nullable
	protected static Item getByNameOrId(String id) {
		try {
			return Item.getItemById(Integer.parseInt(id));
		} catch (NumberFormatException var3) {
			ResourceLocation resourceLocation = new ResourceLocation(id);
			if (IRegistry.ITEM.containsKey(resourceLocation)) {
				return IRegistry.ITEM.getOrDefault(resourceLocation);
			}
			return null;
		}
	}

}
