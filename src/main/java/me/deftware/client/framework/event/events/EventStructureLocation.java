package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.math.position.DoubleBlockPosition;

public class EventStructureLocation extends Event {

    private final BlockPosition pos;
    private final StructureType type;

    public EventStructureLocation(double posX, double posY, double posZ, StructureType type) {
        this.pos = new DoubleBlockPosition(posX, posY, posZ);
        this.type = type;
    }

    public EventStructureLocation(double posX, double posY, double posZ) {
        this.pos = new DoubleBlockPosition(posX, posY, posZ);
        this.type = StructureType.Stronghold;
    }

    public EventStructureLocation(BlockPosition pos, StructureType type) {
        this.pos = pos;
        this.type = type;
    }

    public EventStructureLocation(BlockPosition pos) {
        this.pos = pos;
        this.type = StructureType.Stronghold;
    }

    public BlockPosition getPos() {
        return pos;
    }

    public StructureType getType() {
        return type;
    }

    public enum StructureType {
        Stronghold,
        BuriedTreasure,
        OceanMonument,
        WoodlandMansion,
        OtherMapIcon
    }

}
