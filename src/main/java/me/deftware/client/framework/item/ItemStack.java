package me.deftware.client.framework.item;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.item.effect.StatusEffect;
import me.deftware.client.framework.item.enchantment.Enchantment;
import me.deftware.client.framework.item.types.SwordItem;
import me.deftware.client.framework.item.types.WeaponItem;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.nbt.NbtCompound;
import me.deftware.client.framework.nbt.NbtList;
import me.deftware.client.framework.registry.EnchantmentRegistry;
import me.deftware.client.framework.registry.ItemRegistry;
import me.deftware.client.framework.render.gl.GLX;
import me.deftware.client.framework.util.types.Pair;
import me.deftware.client.framework.world.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ArmorItem;
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

	public static final ItemStack EMPTY = new ItemStack(net.minecraft.item.ItemStack.EMPTY) {
		@Override
		public ItemStack setStack(net.minecraft.item.ItemStack itemStack) {
			if (item != null)
				throw new IllegalStateException("Cannot update reference of global empty stack!");
			return super.setStack(itemStack);
		}
	};

	protected final List<Pair<Enchantment, Integer>> enchantments = new ArrayList<>();

	protected net.minecraft.item.ItemStack itemStack;
	protected Item item;

	public ItemStack(IItem item, int size) {
		this(ItemRegistry.INSTANCE.getItem(item.getAsItem()), size);
	}

	public ItemStack(Block item, int size) {
		this.itemStack = new net.minecraft.item.ItemStack(item.getMinecraftBlock().asItem(), size);
		this.item = ItemRegistry.INSTANCE.getItem(itemStack.getItem());
	}

	public ItemStack(Item item, int size) {
		this.itemStack = new net.minecraft.item.ItemStack(item.getMinecraftItem(), size);
		this.item = item;
	}

	public ItemStack(net.minecraft.item.ItemStack itemStack) {
		setStack(itemStack);
	}

	public ItemStack setStack(net.minecraft.item.ItemStack itemStack) {
		if (itemStack != null) {
			this.itemStack = itemStack;
			this.item = ItemRegistry.INSTANCE.getItem(itemStack.getItem());
		}
		return this;
	}

	public static ItemStack getEmpty() {
		return new ItemStack(net.minecraft.item.ItemStack.EMPTY);
	}

	public static void init(List<net.minecraft.item.ItemStack> original, List<ItemStack> stack) {
		for (int i = 0; i < original.size(); i++)
			stack.set(i, new ItemStack(original.get(i)));
	}

	public static void copyReferences(Iterable<net.minecraft.item.ItemStack> original, List<ItemStack> stack) {
		int index = 0;
		for (net.minecraft.item.ItemStack item : original)
			stack.get(index++).setStack(item);
	}

	public net.minecraft.item.ItemStack getMinecraftItemStack() {
		return itemStack;
	}

	public Item getItem() {
		return item;
	}

	public List<Pair<Enchantment, Integer>> getEnchantments() {
		net.minecraft.nbt.NbtCompound tag = itemStack.getNbt();
		if (tag != null && tag.contains("Enchantments", 9)) {
			net.minecraft.nbt.NbtList list = tag.getList("Enchantments", 10);
			if (!list.isEmpty()) {
				// Found active enchantments
				if (enchantments.size() != list.size()) {
					enchantments.clear();
					Map<net.minecraft.enchantment.Enchantment, Integer> stackEnchantments = EnchantmentHelper.get(itemStack);
					for (net.minecraft.enchantment.Enchantment enchantment : stackEnchantments.keySet()) {
						EnchantmentRegistry.INSTANCE.find(enchantment.getTranslationKey()).ifPresent(e ->
								enchantments.add(new Pair<>(e, stackEnchantments.get(enchantment)))
						);
					}
				}
				return enchantments;
			}
		}
		return Collections.emptyList();
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

	public boolean isEqualItems(ItemStack stack, boolean ignoreDamage) {
		return ignoreDamage ? getMinecraftItemStack().isItemEqualIgnoreDamage(stack.getMinecraftItemStack()) : getMinecraftItemStack().isItemEqual(stack.getMinecraftItemStack());
	}

	public boolean hasStatusEffect(StatusEffect effect) {
		return PotionUtil.getPotionEffects(itemStack).stream()
				.anyMatch(e -> e.getEffectType() == effect.getMinecraftStatusEffect());
	}

	public void setStackDisplayName(ChatMessage name) {
		net.minecraft.nbt.NbtCompound nbt = itemStack.getOrCreateSubNbt("display");
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
		GLX.INSTANCE.modelViewStack(() -> getRenderItem().renderGuiItemIcon(getMinecraftItemStack(), x, y));
	}

	public void renderItemOverlays(int x, int y) {
		GLX.INSTANCE.modelViewStack(() -> getRenderItem().renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, getMinecraftItemStack(), x, y));
	}

	public void renderItemAndEffectIntoGUI(int x, int y) {
		GLX.INSTANCE.modelViewStack(() -> getRenderItem().renderInGui(getMinecraftItemStack(), x, y));
	}

	public void renderItemOverlayIntoGUI(int x, int y, String text) {
		GLX.INSTANCE.modelViewStack(() -> getRenderItem().renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, getMinecraftItemStack(), x, y, text));
	}

	public boolean hasNbt() {
		return itemStack.hasNbt();
	}

	public NbtCompound getNbt() {
		return new NbtCompound(itemStack.getNbt());
	}

	public void setNbtList(String key, NbtList compound) {
		itemStack.setSubNbt(key, compound.getMinecraftListTag());
	}

	public static ArrayList<ItemStack> loadAllItems(NbtCompound compound, int size) {
		DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.getEmpty());
		net.minecraft.nbt.NbtList itemTag = compound.getMinecraftCompound().getList("Items", 10);
		for(int index = 0; index < itemTag.size(); index++) {
			net.minecraft.nbt.NbtCompound item = itemTag.getCompound(index);
			int slotData = item.getByte("Slot") & 255;
			if (slotData < list.size()) {
				list.set(slotData, new ItemStack(net.minecraft.item.ItemStack.fromNbt(new NbtCompound(item).getMinecraftCompound())));
			}
		}
		return new ArrayList<>(list);
	}

}
