package me.deftware.mixin.imp;

import net.minecraft.client.network.PlayerListEntry;

public interface IMixinAbstractClientPlayer {

    PlayerListEntry getPlayerNetworkInfo();

}
