package me.deftware.client.framework.entity.types;

import me.deftware.client.framework.conversion.ConvertedList;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.types.objects.ClonedPlayerMP;
import me.deftware.client.framework.inventory.Inventory;
import me.deftware.client.framework.item.effect.AppliedStatusEffect;
import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.mixin.imp.IMixinEntityLivingBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Deftware
 */
public class EntityPlayer extends LivingEntity {

	private final Inventory inventory;
	private final ConvertedList<AppliedStatusEffect, StatusEffectInstance> statusEffects;

	public EntityPlayer(PlayerEntity entity) {
		super(entity);
		inventory = new Inventory(entity);
		this.statusEffects = new ConvertedList<>(() -> getMinecraftEntity().getStatusEffects(), null, AppliedStatusEffect::new);
	}

	public boolean isCreative() {
		return getMinecraftEntity().isCreative();
	}

	public boolean isSleeping() {
		return getMinecraftEntity().isSleeping();
	}

	public boolean isFlying() {
		return getMinecraftEntity().abilities.flying;
	}

	public void setFlying(boolean flag) {
		getMinecraftEntity().abilities.flying = flag;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public List<AppliedStatusEffect> getStatusEffects() {
		return statusEffects.poll();
	}

	public float getSaturationLevel() {
		return getMinecraftEntity().getHungerManager().getSaturationLevel();
	}

	public UUID getUUID() {
		return ((PlayerEntity) entity).getGameProfile().getId();
	}

	public String getUsername() {
		return ((PlayerEntity) entity).getGameProfile().getName();
	}

	@Override
	public PlayerEntity getMinecraftEntity() {
		return (PlayerEntity) entity;
	}

	public float getCooldown() {
		return getMinecraftEntity().getAttackCooldownProgress(0);
	}

	public boolean isAtEdge() {
		return Objects.requireNonNull(MinecraftClient.getInstance().world).getCollisions(getMinecraftEntity(), getMinecraftEntity().getBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001), (foundEntity) -> true).count() == 0;
	}

	public void openInventory() {
		Minecraft.RENDER_THREAD.add(() -> MinecraftClient.getInstance().openScreen(new InventoryScreen(getMinecraftEntity())));
	}

	public int getFoodLevel() {
		return getMinecraftEntity().getHungerManager().getFoodLevel();
	}

	public void respawn() {
		getMinecraftEntity().requestRespawn();
	}

	public int getItemInUseCount() {
		return ((IMixinEntityLivingBase) getMinecraftEntity()).getActiveItemStackUseCount(); // TODO
	}

	public void doJump() {
		getMinecraftEntity().jump();
	}

	public int getItemInUseMaxCount() {
		return getMinecraftEntity().getItemUseTimeLeft();
	}

	public void drawPlayer(int posX, int posY, int scale) {
		InventoryScreen.drawEntity(posX, posY, scale, 0, 0, getMinecraftEntity());
	}

	public Entity clone() {
		return Entity.newInstance(new ClonedPlayerMP(getMinecraftEntity()));
	}

	public float getFlySpeed() {
		return getMinecraftEntity().abilities.getFlySpeed();
	}

	public void setFlySpeed(float speed) {
		getMinecraftEntity().abilities.setFlySpeed(speed);
	}

	public float getWalkSpeed() {
		return getMinecraftEntity().abilities.getWalkSpeed();
	}

	public void setWalkSpeed(float speed) {
		getMinecraftEntity().abilities.setWalkSpeed(speed);
	}

}
