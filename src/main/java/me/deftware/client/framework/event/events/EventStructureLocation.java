package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.world.IBlockPos;

public class EventStructureLocation extends Event {
    private IBlockPos pos;

    public EventStructureLocation(double posX, double posY, double posZ) {
        this.pos = new IBlockPos(posX, posY, posZ);
    }

    public EventStructureLocation(IBlockPos pos) {
        this.pos = pos;
    }

    public IBlockPos getPos() {
        return pos;
    }
}
