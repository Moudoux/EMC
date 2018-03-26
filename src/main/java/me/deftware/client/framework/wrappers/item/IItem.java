package me.deftware.client.framework.wrappers.item;


import me.deftware.mixin.imp.IMixinItemTool;
import net.minecraft.item.*;

public class IItem {

	private Item item;

	public IItem(Item item) {
		this.item = item;
	}

	public IItem(String name) {
		item = Item.getByNameOrId(name);
	}

	public Item getItem() {
		return item;
	}

	public String getName() {
		return item.getItemStackDisplayName(new ItemStack(item));
	}

	public int getID() {
		return item.getIdFromItem(item);
	}

	public boolean isValidItem() {
		return item != null;
	}

	public float getAttackDamage() {
		return ((ItemSword) item).getDamageVsEntity() + 3.0F;
	}

	public float getDamageVsEntity() {
		return ((IMixinItemTool) item).getDamageVsEntity();
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
		} else if (type.equals(IItemType.ItemHoe)) {
			return item instanceof ItemHoe;
		}
		return false;
	}

	public static enum IItemType {
		ItemPotion, ItemFishingRod, ItemFood, ItemSword, ItemTool, ItemNameTag, ItemBlock, ItemHoe
	}

}
