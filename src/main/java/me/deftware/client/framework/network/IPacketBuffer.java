package me.deftware.client.framework.network;

import io.netty.buffer.Unpooled;
import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.util.PacketByteBuf;

public class IPacketBuffer {

    public PacketByteBuf buffer;

    public IPacketBuffer() {
        this.buffer = new PacketByteBuf(Unpooled.buffer());
    }

    public void writeItemStack(IItemStack stack) {
        buffer.writeItemStack(stack.getStack());
    }

}
