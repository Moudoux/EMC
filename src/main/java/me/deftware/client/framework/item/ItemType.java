package me.deftware.client.framework.item;

import net.minecraft.item.*;

/**
 * @author Deftware
 */
public enum ItemType {

	ItemPotion, ItemFishingRod, ItemFood, ItemSword, ItemTool, ItemNameTag, ItemBlock, ItemHoe, SplashPotion,
	ItemSoup, ItemShulkerBox, WritableBook;

	public boolean instanceOf(Item emcItem) {
		net.minecraft.item.Item item = emcItem.getMinecraftItem();
		if (this.equals(ItemFishingRod)) {
			return item instanceof FishingRodItem;
		} else if (this.equals(ItemPotion)) {
			return item instanceof PotionItem;
		} else if (this.equals(SplashPotion)) {
			return item == Items.SPLASH_POTION;
		} else if (this.equals(ItemFood)) {
			return item.isFood();
		} else if (this.equals(ItemSword)) {
			return item instanceof SwordItem;
		} else if (this.equals(ItemTool)) {
			return item instanceof ToolItem;
		} else if (this.equals(ItemNameTag)) {
			return item instanceof NameTagItem;
		} else if (this.equals(ItemBlock)) {
			return item instanceof BlockItem;
		} else if (this.equals(ItemSoup)) {
			return item instanceof MushroomStewItem;
		} else if (this.equals(WritableBook)) {
			return item instanceof WritableBookItem;
		} else if (this.equals(ItemHoe)) {
			return item instanceof HoeItem;
		} else if (this.equals(ItemShulkerBox)) {
			return item instanceof BlockItem && item.getTranslationKey().contains("shulker_box");
		}
		return false;
	}
	
}
