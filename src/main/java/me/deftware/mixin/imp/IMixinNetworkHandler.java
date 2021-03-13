package me.deftware.mixin.imp;

import me.deftware.client.framework.world.player.PlayerEntry;

import java.util.Map;
import java.util.UUID;

public interface IMixinNetworkHandler {

    Map<UUID, PlayerEntry> getPlayerEntryMap();

}
