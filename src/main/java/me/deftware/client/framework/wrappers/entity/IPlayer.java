package me.deftware.client.framework.wrappers.entity;

import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class IPlayer {

    private PlayerEntity player;

    public IPlayer(PlayerEntity player) {
        this.player = player;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public float getHealth() {
        return player.getHealth();
    }

    public String getName() {
        return player.getGameProfile().getName();
    }

    public boolean isSelf() {
        if (player == MinecraftClient.getInstance().player
                || player.getName().equals(MinecraftClient.getInstance().getSession().getUsername())) {
            return true;
        }
        return false;
    }

    public IItemStack getHeldItem() {
        if (player.inventory.getMainHandStack() != null) {
            return new IItemStack(player.inventory.getMainHandStack());
        }
        return null;
    }

    public double getPosX() {
        return player.getX();
    }

    public double getPosY() {
        return player.getY();
    }

    public double getPosZ() {
        return player.getZ();
    }

    public double getLastTickPosX() {
        return player.lastRenderX;
    }

    public double getLastTickPosY() {
        return player.lastRenderY;
    }

    public double getLastTickPosZ() {
        return player.lastRenderZ;
    }

    public boolean isCreative() {
        return player.isCreative();
    }

    public void setGlowing(boolean state) {
        player.setGlowing(state);
    }

}