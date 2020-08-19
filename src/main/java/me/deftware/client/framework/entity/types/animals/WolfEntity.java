package me.deftware.client.framework.entity.types.animals;

import me.deftware.client.framework.entity.types.EntityPlayer;
import me.deftware.client.framework.entity.types.LivingEntity;

/**
 * @author Deftware
 */
public class WolfEntity extends LivingEntity {

	public WolfEntity(net.minecraft.entity.Entity entity) {
		super(entity);
	}

	public net.minecraft.entity.passive.WolfEntity getMinecraftEntity() {
		return (net.minecraft.entity.passive.WolfEntity) entity;
	}

	public boolean isPlayerOwned(EntityPlayer player) {
		return getMinecraftEntity().isOwner(player.getMinecraftEntity());
	}

}
