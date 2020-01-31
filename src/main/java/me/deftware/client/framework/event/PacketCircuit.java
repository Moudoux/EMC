package me.deftware.client.framework.event;

import me.deftware.client.framework.network.IPacket;

import java.util.ArrayList;
import java.util.HashMap;

public class PacketCircuit {

    private static ArrayList<PacketCircuitHandler> circuits = new ArrayList<>();

    public static void registerHandler(PacketCircuitHandler handler) {
        circuits.add(handler);
    }

    public static IPacket handlePacket(IPacket packet) {
        Class<? extends IPacket> type = packet.getClass();
        for(PacketCircuitHandler handler : circuits) {
            packet = handler.onPacket(packet);
        }
        return packet;
    }

}
