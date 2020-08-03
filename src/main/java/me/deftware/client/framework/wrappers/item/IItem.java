package me.deftware.client.framework.wrappers.item;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.mixin.imp.IMixinItemTool;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IItem {

    private Item item;

    public IItem(String name) {
        item = getByName(name);
    }

    public IItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public ChatMessage getName() {
        return new ChatMessage().fromText(item.getName());
    }

    public String getTranslationKey() {
        return item.getTranslationKey();
    }

    public String getItemKey() {
        String key = getTranslationKey();
        if (key.startsWith("item.minecraft")) {
            key = key.substring("item.minecraft.".length());
        } else if (key.startsWith("block.minecraft")) {
            key = key.substring("block.minecraft.".length());
        }
        return key;
    }

    public int getID() {
        return Item.getRawId(item);
    }

    public boolean isValidItem() {
        return item != null;
    }

    public float getAttackDamage() {
        return ((SwordItem) item).getAttackDamage() + 3.0F;
    }

    public float getDamageVsEntity() {
        return ((IMixinItemTool) item).getAttackDamage();
    }

    public boolean isThrowable() {
        return item instanceof BowItem || item instanceof SnowballItem || item instanceof EggItem
                || item instanceof EnderPearlItem || item instanceof SplashPotionItem
                || item instanceof LingeringPotionItem || item instanceof FishingRodItem;
    }

    public boolean instanceOf(IItemType type) {
        if (type.equals(IItemType.ItemFishingRod)) {
            return item instanceof FishingRodItem;
        } else if (type.equals(IItemType.ItemPotion)) {
            return item instanceof PotionItem;
        } else if (type.equals(IItemType.SplashPotion)) {
            return item == Items.SPLASH_POTION;
        } else if (type.equals(IItemType.ItemFood)) {
            return item.getGroup() == ItemGroup.FOOD;
        } else if (type.equals(IItemType.ItemSword)) {
            return item instanceof SwordItem;
        } else if (type.equals(IItemType.ItemTool)) {
            return item instanceof ToolItem;
        } else if (type.equals(IItemType.ItemNameTag)) {
            return item instanceof NameTagItem;
        } else if (type.equals(IItemType.ItemBlock)) {
            return item instanceof BlockItem;
        } else if (type.equals(IItemType.ItemSoup)) {
            return item instanceof MushroomStewItem;
        } else if (type.equals(IItemType.WritableBook)) {
            return item instanceof WritableBookItem;
        } else if (type.equals(IItemType.ItemHoe)) {
            return item instanceof HoeItem;
        } else if (type.equals(IItemType.ItemShulkerBox)) {
            return item instanceof BlockItem && item.getTranslationKey().contains("shulker_box");
        }
        return false;
    }

    public enum IItemType {
        ItemPotion, ItemFishingRod, ItemFood, ItemSword, ItemTool, ItemNameTag, ItemBlock, ItemHoe, SplashPotion,
        ItemSoup, ItemShulkerBox, WritableBook
    }

    protected static Item getByName(String id) {
        Identifier resourceLocation = new Identifier(id);
        if (Registry.ITEM.containsId(resourceLocation)) {
            return Registry.ITEM.get(resourceLocation);
        }
        return null;
    }

}
