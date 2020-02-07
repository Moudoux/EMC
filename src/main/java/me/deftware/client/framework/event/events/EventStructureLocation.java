package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.wrappers.world.IBlockPos;

public class EventStructureLocation extends Event {
    private IBlockPos pos;

    private StructureType type;

    public EventStructureLocation(double posX, double posY, double posZ, StructureType type) {
        this.pos = new IBlockPos(posX, posY, posZ);
        this.type = type;
    }

    public EventStructureLocation(double posX, double posY, double posZ) {
        this.pos = new IBlockPos(posX, posY, posZ);
        this.type = StructureType.Stronghold;
    }

    public EventStructureLocation(IBlockPos pos, StructureType type) {
        this.pos = pos;
        this.type = type;
    }

    public EventStructureLocation(IBlockPos pos) {
        this.pos = pos;
        this.type = StructureType.Stronghold;
    }

    public IBlockPos getPos() {
        return pos;
    }

    public StructureType getType() {
        return type;
    }

    public enum StructureType {
        Stronghold, BuriedTreasure
    }
}
