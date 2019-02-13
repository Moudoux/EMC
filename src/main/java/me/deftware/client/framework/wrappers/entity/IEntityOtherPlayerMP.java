package me.deftware.client.framework.wrappers.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class IEntityOtherPlayerMP extends OtherClientPlayerEntity {

    public IEntityOtherPlayerMP() {
        super(MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getGameProfile());
        clonePlayer(MinecraftClient.getInstance().player, true);
        setPositionAndAngles(MinecraftClient.getInstance().player);
        headYaw = MinecraftClient.getInstance().player.headYaw;
    }

    public IEntityOtherPlayerMP(ClientWorld worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
    }

    public void clonePlayer(PlayerEntity oldPlayer, boolean respawnFromEnd) {
        if (respawnFromEnd) {
            inventory.clone(oldPlayer.inventory);
            setHealth(oldPlayer.getHealth());
            hungerManager = oldPlayer.getHungerManager();
            experienceLevel = oldPlayer.experienceLevel;
            experienceBarProgress = oldPlayer.experienceBarProgress;
            experience = oldPlayer.experience;
            setScore(oldPlayer.getScore());
            field_6020 = oldPlayer.method_5656();
            field_6028 = oldPlayer.method_5843();
        } else if (world.getGameRules().getBoolean("keepInventory") || oldPlayer.isSpectator()) {
            inventory.clone(oldPlayer.inventory);
            experienceLevel = oldPlayer.experienceLevel;
            experienceBarProgress = oldPlayer.experienceBarProgress;
            experience = oldPlayer.experience;
            setScore(oldPlayer.getScore());
        }
        enchantmentTableSeed = oldPlayer.getEnchantmentTableSeed();
        getDataTracker().set(PlayerEntity.PLAYER_MODEL_BIT_MASK, oldPlayer.getDataTracker().get(PlayerEntity.PLAYER_MODEL_BIT_MASK));
    }

}
