package me.deftware.mixin.imp;

import net.minecraft.network.Packet;

public interface IMixinNetworkManager {

	void sendPacketImmediately(Packet<?> packet);

}
