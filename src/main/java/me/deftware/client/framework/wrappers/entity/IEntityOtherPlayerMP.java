package me.deftware.client.framework.wrappers.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;

public class IEntityOtherPlayerMP extends OtherClientPlayerEntity {

    public IEntityOtherPlayerMP() {
        super(MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getGameProfile());
        clonePlayer(MinecraftClient.getInstance().player, true);
        setPositionAndAngles(MinecraftClient.getInstance().player.x, MinecraftClient.getInstance().player.y, MinecraftClient.getInstance().player.z, MinecraftClient.getInstance().player.yaw, MinecraftClient.getInstance().player.pitch);
        headYaw = MinecraftClient.getInstance().player.headYaw;
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
            lastPortalDirectionVector = oldPlayer.getLastPortalDirectionVector();
            lastPortalDirection = oldPlayer.getLastPortalDirection();
        } else if (world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) || oldPlayer.isSpectator()) {
            inventory.clone(oldPlayer.inventory);
            experienceLevel = oldPlayer.experienceLevel;
            experienceProgress = oldPlayer.experienceProgress;
            totalExperience = oldPlayer.totalExperience;
            setScore(oldPlayer.getScore());
        }
        enchantmentTableSeed = oldPlayer.getEnchantmentTableSeed();
        getDataTracker().set(PlayerEntity.PLAYER_MODEL_BIT_MASK, oldPlayer.getDataTracker().get(PlayerEntity.PLAYER_MODEL_BIT_MASK));
    }

}
