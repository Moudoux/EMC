package me.deftware.client.framework.entity.types.objects;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;

import java.util.Objects;

/**
 * @author Deftware
 */
@SuppressWarnings("EntityConstructor")
public class ClonedPlayerMP extends OtherClientPlayerEntity {

	public ClonedPlayerMP(PlayerEntity entity) {
		super(Objects.requireNonNull(MinecraftClient.getInstance().world), entity.getGameProfile());
		clonePlayer(entity, true);
		refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.yaw, entity.pitch);
		headYaw = entity.headYaw;
	}

	public void clonePlayer(PlayerEntity oldPlayer, boolean respawnFromEnd) {
		if (respawnFromEnd) {
			inventory.clone(oldPlayer.inventory);
			setHealth(oldPlayer.getHealth());
			hungerManager = oldPlayer.getHungerManager();
			experienceLevel = oldPlayer.experienceLevel;
			experienceProgress = oldPlayer.experienceProgress;
			totalExperience = oldPlayer.totalExperience;
			setScore(oldPlayer.getScore());
			copyFrom(oldPlayer);
		} else if (world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) || oldPlayer.isSpectator()) {
			inventory.clone(oldPlayer.inventory);
			experienceLevel = oldPlayer.experienceLevel;
			experienceProgress = oldPlayer.experienceProgress;
			totalExperience = oldPlayer.totalExperience;
			setScore(oldPlayer.getScore());
		}
		enchantmentTableSeed = oldPlayer.getEnchantmentTableSeed();
		if (getDataTracker() != null) {
			getDataTracker().set(PlayerEntity.PLAYER_MODEL_PARTS, oldPlayer.getDataTracker().get(PlayerEntity.PLAYER_MODEL_PARTS));
		}
	}

}

