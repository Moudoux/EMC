package me.deftware.mixin.imp;

import me.deftware.client.framework.network.packets.CPacketUseEntity;

public interface IMixinPlayerInteractEntityC2SPacket {

    CPacketUseEntity.Type getActionType();

    void setActionType(CPacketUseEntity.Type type);

}
