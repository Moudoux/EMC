package me.deftware.client.framework.event.events;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.event.Event;
import net.minecraft.fluid.FluidState;

public class EventSubmergedTransition extends Event {
    private @Setter @Getter FluidState fluidState;

    public EventSubmergedTransition() {}
}
