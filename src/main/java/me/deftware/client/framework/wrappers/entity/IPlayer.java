package me.deftware.client.framework.wrappers.entity;


import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class IPlayer {

    private ClientPlayerEntity player;

    public IPlayer(ClientPlayerEntity player) {
        this.player = player;
    }

    public ClientPlayerEntity getPlayer() {
        return player;
    }

    public float getHealth() {
        return player.getHealth();
    }

    public String getName() {
        return player.getGameProfile().getName();
    }

    public String getFormattedDisplayName() {
        return player.getDisplayName().getFormattedText();
    }

    public float getNametagSize() {
        return MinecraftClient.getInstance().player.distanceTo(player) / 2.5F <= 1.5F ? 2.0F
                : MinecraftClient.getInstance().player.distanceTo(player) / 2.5F;
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
        return player.x;
    }

    public double getPosY() {
        return player.y;
    }

    public double getPosZ() {
        return player.z;
    }

    public double getLastTickPosX() {
        return player.prevRenderX;
    }

    public double getLastTickPosY() {
        return player.prevRenderY;
    }

    public double getLastTickPosZ() {
        return player.prevRenderZ;
    }

    public boolean isCreative() {
        return player.isCreative();
    }

    public float getHeight() {
        return player.getHeight();
    }

    public void setGlowing(boolean state) {
        player.setGlowing(state);
    }

}