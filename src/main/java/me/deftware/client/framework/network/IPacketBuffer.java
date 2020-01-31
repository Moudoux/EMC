package me.deftware.client.framework.network;

import io.netty.buffer.Unpooled;
import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.PacketByteBuf;

public class IPacketBuffer {

    public PacketByteBuf buffer;

    public IPacketBuffer() {
        this.buffer = new PacketByteBuf(Unpooled.buffer());
    }

    public IPacketBuffer(PacketByteBuf buffer) {
        this.buffer = buffer;
    }

    public void writeItemStack(IItemStack stack) {
        buffer.writeItemStack(stack.getStack());
    }

    public void writeDouble(double value) {
        buffer.writeDouble(value);
    }

    public void writeFloat(float value) {
        buffer.writeFloat(value);
    }

    public void writeBoolean(boolean value) {
        buffer.writeBoolean(value);
    }

    public void writeString(String value) {
        buffer.writeString(value);
    }

    public double readDouble() {
        return buffer.readDouble();
    }

    public float readFloat() {
        return buffer.readFloat();
    }

    public boolean readBoolean() {
        return buffer.readBoolean();
    }

    public String readString() {
        return buffer.readString();
    }

    public IItemStack readItemStack() {
        return new IItemStack(buffer.readItemStack());
    }

}
