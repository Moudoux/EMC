package me.deftware.client.framework.event;

import me.deftware.client.framework.network.IPacket;

public interface PacketCircuitHandler {

    IPacket onPacket(IPacket packet);

}
