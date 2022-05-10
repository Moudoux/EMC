package me.deftware.mixin.imp;

import net.minecraft.client.network.NetworkPlayerInfo;

public interface IMixinAbstractClientPlayer {

    NetworkPlayerInfo getPlayerNetworkInfo();

}
