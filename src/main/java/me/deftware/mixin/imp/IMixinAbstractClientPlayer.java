package me.deftware.mixin.imp;

import net.minecraft.client.network.ScoreboardEntry;

public interface IMixinAbstractClientPlayer {

    ScoreboardEntry getPlayerNetworkInfo();

}
