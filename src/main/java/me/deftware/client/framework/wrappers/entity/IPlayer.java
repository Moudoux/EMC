package me.deftware.client.framework.wrappers.entity;

import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class IPlayer extends IEntity {

    private PlayerEntity player;

    public IPlayer(PlayerEntity player) {
        super(player);
        this.player = player;
    }

    public String getUUID() {
        return player.getGameProfile().getId().toString();
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public String getName() {
        return player.getGameProfile().getName();
    }

    public boolean isSelf() {
        return player == MinecraftClient.getInstance().player
                || player.getName().equals(MinecraftClient.getInstance().getSession().getUsername());
    }

    public IItemStack getHeldItem() {
        if (player.inventory.getMainHandStack() != null) {
            return new IItemStack(player.inventory.getMainHandStack());
        }
        return null;
    }

    public boolean isCreative() {
        return player.isCreative();
    }

    public void setGlowing(boolean state) {
        player.setGlowing(state);
    }

}