package me.deftware.client.framework.network;

import io.netty.buffer.Unpooled;
import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.network.PacketBuffer;

public class IPacketBuffer {

    public PacketBuffer buffer;

    public IPacketBuffer() {
        this.buffer = new PacketBuffer(Unpooled.buffer());
    }

    public void writeItemStack(IItemStack stack) {
        buffer.writeItemStack(stack.getStack());
    }

}
