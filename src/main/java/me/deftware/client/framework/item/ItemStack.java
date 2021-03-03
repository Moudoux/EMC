package me.deftware.client.framework.item;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.conversion.ConvertedList;
import me.deftware.client.framework.item.effect.AppliedStatusEffect;
import me.deftware.client.framework.item.effect.StatusEffect;
import me.deftware.client.framework.item.enchantment.Enchantment;
import me.deftware.client.framework.item.types.SwordItem;
import me.deftware.client.framework.item.types.WeaponItem;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.nbt.NbtCompound;
import me.deftware.client.framework.nbt.NbtList;
import me.deftware.client.framework.registry.EnchantmentRegistry;
import me.deftware.client.framework.util.types.Pair;
import me.deftware.client.framework.world.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ArmorItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;

import java.util.*;

/**
 * @author Deftware
 */
public class ItemStack {

	public static final ItemStack EMPTY = new ItemStack(net.minecraft.item.ItemStack.EMPTY);

	protected ConvertedList<AppliedStatusEffect, StatusEffectInstance> statusEffects;
	protected final List<Pair<Enchantment, Integer>> enchantments = new ArrayList<>();
	protected final net.minecraft.item.ItemStack itemStack;
	protected final Item item;

	public ItemStack(IItem item, int size) {
		this(Item.newInstance(item.getAsItem()), size);
	}

	public ItemStack(Block item, int size) {
		this.itemStack = new net.minecraft.item.ItemStack(item.getMinecraftBlock().asItem(), size);
		this.item = Item.newInstance(itemStack.getItem());
		initConversions();
	}

	public ItemStack(Item item, int size) {
		this.itemStack = new net.minecraft.item.ItemStack(item.getMinecraftItem(), size);
		this.item = item;
		initConversions();
	}

	public ItemStack(net.minecraft.item.ItemStack itemStack) {
		this.itemStack = itemStack;
		this.item = Item.newInstance(itemStack.getItem());
		initConversions();
	}

	private void initConversions() {
		this.statusEffects = new ConvertedList<>(() -> PotionUtil.getPotionEffects(itemStack), null, AppliedStatusEffect::new);
	}

	public net.minecraft.item.ItemStack getMinecraftItemStack() {
		return itemStack;
	}

	public Item getItem() {
		return item;
	}

	public List<Pair<Enchantment, Integer>> getEnchantments() {
		Map<net.minecraft.enchantment.Enchantment, Integer> stackEnchantments = EnchantmentHelper.get(itemStack);
		if (enchantments.size() != stackEnchantments.size()) {
			for (net.minecraft.enchantment.Enchantment enchantment : stackEnchantments.keySet()) {
				EnchantmentRegistry.INSTANCE.find(enchantment.getTranslationKey()).ifPresent(e ->
						enchantments.add(new Pair<>(e, stackEnchantments.get(enchantment)))
				);
			}
		}
		return enchantments;
	}

	public int getStackProtectionAmount() {
		int protection = EnchantmentHelper.getProtectionAmount(Collections.singletonList(itemStack), DamageSource.GENERIC);
		if (item.getMinecraftItem() instanceof ArmorItem) {
			protection += ((ArmorItem) item.getMinecraftItem()).getProtection();
		}
		return protection;
	}

	public float getStackAttackDamage() {
		float damage = EnchantmentHelper.getAttackDamage(itemStack, EntityGroup.DEFAULT);
		if (item instanceof SwordItem) {
			damage += ((SwordItem) item).getAttackDamage();
		} else if (item instanceof WeaponItem) {
			damage += ((WeaponItem) item).getAttackDamage();
		}
		return damage;
	}

	public boolean isEnchantable() {
		return itemStack.isEnchantable();
	}

	public boolean isDamageable() {
		return itemStack.isDamageable();
	}

	public boolean isDamaged() {
		return itemStack.isDamaged();
	}

	public boolean isEmpty() {
		return item.getID() == 0 || item.isAir();
	}

	public int getMaxSize() {
		return itemStack.getMaxCount();
	}

	public int getCount() {
		return itemStack.getCount();
	}

	public void setCount(int count) {
		itemStack.setCount(count);
	}

	public int getDamage() {
		return MathHelper.clamp(getRawDamage(), 0, getMaxDamage());
	}

	public int getRawDamage() {
		return itemStack.getDamage();
	}

	public int getMaxDamage() {
		return Math.max(getRawMaxDamage(), 0);
	}

	public int getRawMaxDamage() {
		return itemStack.getMaxDamage();
	}

	public ChatMessage getDisplayName() {
		return new ChatMessage().fromText(itemStack.getName());
	}

	public float getStrVsBlock(BlockPosition pos) {
		return itemStack.getMiningSpeedMultiplier(Objects.requireNonNull(MinecraftClient.getInstance().world).getBlockState(pos.getMinecraftBlockPos()));
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ItemStack) {
			return net.minecraft.item.ItemStack.areEqual(getMinecraftItemStack(), ((ItemStack) object).getMinecraftItemStack());
		}
		return false;
	}

	public List<AppliedStatusEffect> getAppliedStatusEffects() {
		return statusEffects.poll();
	}

	public boolean isEqualItems(ItemStack stack, boolean ignoreDamage) {
		return ignoreDamage ? getMinecraftItemStack().isItemEqualIgnoreDamage(stack.getMinecraftItemStack()) : getMinecraftItemStack().isItemEqual(stack.getMinecraftItemStack());
	}

	public boolean hasStatusEffect(StatusEffect effect) {
		return PotionUtil.getPotionEffects(itemStack).stream()
				.anyMatch(e -> e.getEffectType() == effect.getMinecraftStatusEffect());
	}

	public void setStackDisplayName(ChatMessage name) {
		CompoundTag nbt = itemStack.getOrCreateSubTag("display");
		nbt.putString("Name", LiteralText.Serializer.toJson(name.build()));
	}
	
	public void addEnchantment(Enchantment enchantment, int level) {
		itemStack.addEnchantment(enchantment.getMinecraftEnchantment(), level);
	}

	public int getRarity() {
		if (itemStack.getRarity() == Rarity.COMMON) {
			return 0;
		} else if (itemStack.getRarity() == Rarity.UNCOMMON) {
			return 1;
		} else if (itemStack.getRarity() == Rarity.RARE) {
			return 2;
		} else if (itemStack.getRarity() == Rarity.EPIC) {
			return 3;
		}
		return 0;
	}

	private static ItemRenderer getRenderItem() {
		return MinecraftClient.getInstance().getItemRenderer();
	}

	public static void setRenderZLevel(float z) {
		getRenderItem().zOffset = z;
	}

	public void renderItemIntoGUI(int x, int y) {
		getRenderItem().renderGuiItemIcon(getMinecraftItemStack(), x, y);
	}

	public void renderItemOverlays(int x, int y) {
		getRenderItem().renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, getMinecraftItemStack(), x, y);
	}

	public void renderItemAndEffectIntoGUI(int x, int y) {
		getRenderItem().renderInGui(getMinecraftItemStack(), x, y);
	}

	public void renderItemOverlayIntoGUI(int x, int y, String text) {
		getRenderItem().renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, getMinecraftItemStack(), x, y, text);
	}

	public boolean hasNbt() {
		return itemStack.hasTag();
	}

	public NbtCompound getNbt() {
		return new NbtCompound(itemStack.getTag());
	}

	public void setNbtList(String key, NbtList compound) {
		itemStack.putSubTag(key, compound.getMinecraftListTag());
	}

	public static ArrayList<ItemStack> loadAllItems(NbtCompound compound, int size) {
		DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
		ListTag itemTag = compound.getMinecraftCompound().getList("Items", 10);
		for(int index = 0; index < itemTag.size(); index++) {
			CompoundTag item = itemTag.getCompound(index);
			int slotData = item.getByte("Slot") & 255;
			if (slotData < list.size()) {
				list.set(slotData, new ItemStack(net.minecraft.item.ItemStack.fromNbt(new NbtCompound(item).getMinecraftCompound())));
			}
		}
		return new ArrayList<>(list);
	}

}
