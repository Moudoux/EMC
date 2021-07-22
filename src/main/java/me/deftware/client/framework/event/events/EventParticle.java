package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

public class EventParticle extends Event {

    private String id;
    private double x, y, z, velocityX, velocityZ, velocityY;

    public EventParticle(String id, double x, double y, double z, double velocityX, double velocityZ, double velocityY) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velocityX = velocityX;
        this.velocityZ = velocityZ;
        this.velocityY = velocityY;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityZ() {
        return velocityZ;
    }

    public double getVelocityY() {
        return velocityY;
    }

}
