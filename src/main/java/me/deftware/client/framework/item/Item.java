package me.deftware.client.framework.item;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.item.types.BlockItem;
import me.deftware.client.framework.item.types.FishingRodItem;
import me.deftware.client.framework.item.types.PotionItem;
import me.deftware.client.framework.item.types.RangedWeaponItem;
import me.deftware.client.framework.item.types.SwordItem;
import me.deftware.client.framework.item.types.ToolItem;
import me.deftware.client.framework.item.types.TridentItem;
import me.deftware.client.framework.item.types.*;
import net.minecraft.block.Blocks;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.*;

/**
 * @author Deftware
 */
public class Item implements IItem {

	protected final net.minecraft.item.Item item;

	public static Item newInstance(net.minecraft.item.Item item) {
		if (item instanceof ArmorItem) {
			return new ArmourItem(item);
		} else if (item instanceof CrossbowItem) {
			return new me.deftware.client.framework.item.types.CrossbowItem(item);
		} else if (item instanceof BowItem) {
			return new me.deftware.client.framework.item.types.BowItem(item);
		} else if (item instanceof net.minecraft.item.SwordItem) {
			return new SwordItem(item);
		} else if (item instanceof net.minecraft.item.ToolItem) {
			return new ToolItem(item);
		} else if (item instanceof net.minecraft.item.BlockItem) {
			return new BlockItem(item);
		} else if (item instanceof net.minecraft.item.PotionItem) {
			return new PotionItem(item);
		} else if (item instanceof net.minecraft.item.FishingRodItem) {
			return new FishingRodItem(item);
		} else if (item instanceof net.minecraft.item.TridentItem) {
			return new TridentItem(item);
		} else if (item instanceof net.minecraft.item.RangedWeaponItem) {
			return new RangedWeaponItem(item);
		}
		return new Item(item);
	}

	protected Item(net.minecraft.item.Item item) {
		this.item = item;
	}

	public net.minecraft.item.Item getMinecraftItem() {
		return item;
	}

	public String getIdentifierKey() {
		String key = getTranslationKey();
		if (key.startsWith("item.minecraft")) {
			key = key.substring("item.minecraft.".length());
		} else if (key.startsWith("block.minecraft")) {
			key = key.substring("block.minecraft.".length());
		}
		return key;
	}

	public boolean isAir() {
		return getMinecraftItem() == Blocks.AIR.asItem();
	}

	public String getTranslationKey() {
		return item.getTranslationKey();
	}

	public int getID() {
		return net.minecraft.item.Item.getRawId(item);
	}

	public ChatMessage getName() {
		return new ChatMessage().fromText(item.getName());
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Item) {
			return ((Item) object).getMinecraftItem() == getMinecraftItem() || getIdentifierKey().equals(((Item) object).getIdentifierKey());
		}
		return false;
	}

	public boolean isThrowable() {
		return item instanceof BowItem || item instanceof CrossbowItem || item instanceof SnowballItem || item instanceof EggItem
				|| item instanceof EnderPearlItem || item instanceof SplashPotionItem
				|| item instanceof LingeringPotionItem || item instanceof net.minecraft.item.FishingRodItem || item instanceof net.minecraft.item.TridentItem;
	}

	public boolean instanceOf(ItemType type) {
		return type.instanceOf(this);
	}

	@Override
	public net.minecraft.item.Item getAsItem() {
		return item;
	}

}
