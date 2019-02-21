package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.network.Packet;
import net.minecraft.server.network.packet.BookUpdateC2SPacket;
import net.minecraft.util.Hand;

public class ICPacketEditBook extends IPacket {

    public ICPacketEditBook(Packet<?> packet) {
        super(packet);
    }

    public ICPacketEditBook(IItemStack book) {
        super(new BookUpdateC2SPacket(book.getStack(), true, Hand.MAIN));
    }

}
