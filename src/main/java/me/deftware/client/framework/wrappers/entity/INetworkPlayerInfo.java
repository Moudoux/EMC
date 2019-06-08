package me.deftware.client.framework.wrappers.entity;

import net.minecraft.client.network.PlayerListEntry;

public class INetworkPlayerInfo {

    private PlayerListEntry data;

    public INetworkPlayerInfo(PlayerListEntry data) {
        this.data = data;
    }

    public boolean isNull() {
        return data == null;
    }

    public boolean isSurvivalOrAdventure() {
        return data.getGameMode().isSurvivalLike();
    }

    public boolean isCreative() {
        return data.getGameMode().isCreative();
    }

}
