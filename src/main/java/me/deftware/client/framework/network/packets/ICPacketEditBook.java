package me.deftware.client.framework.network.packets;

import me.deftware.client.framework.network.IPacket;
import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEditBook;
import net.minecraft.util.EnumHand;

public class ICPacketEditBook extends IPacket {

    public ICPacketEditBook(Packet<?> packet) {
        super(packet);
    }

    public ICPacketEditBook(IItemStack book) {
        super(new CPacketEditBook(book.getStack(), true, EnumHand.MAIN_HAND));
    }

}
