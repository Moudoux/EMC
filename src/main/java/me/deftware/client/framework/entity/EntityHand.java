package me.deftware.client.framework.entity;

import net.minecraft.util.Hand;

/**
 * @author Deftware
 */
public enum EntityHand {

    MainHand, OffHand, None;

    public Hand getMinecraftHand() {
        if (this == None) {
            throw new IllegalStateException("Cannot convert " + this.name() + " to Minecraft hand");
        }
        return Hand.values()[this.ordinal()];
    }

    public static EntityHand of(Hand hand) {
        return EntityHand.values()[hand.ordinal()];
    }

}
