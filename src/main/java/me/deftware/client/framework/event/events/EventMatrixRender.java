package me.deftware.client.framework.event.events;

public class EventMatrixRender extends EventRender2D {

    public EventMatrixRender() { }

    @Deprecated
    public EventMatrixRender(float partialTicks) {
        this.partialTicks = partialTicks;
    }

}
