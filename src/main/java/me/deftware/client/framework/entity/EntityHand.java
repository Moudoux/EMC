package me.deftware.client.framework.entity;

import net.minecraft.util.Hand;

/**
 * @author Deftware
 */
public enum EntityHand {

    MainHand, OffHand, None;

    public Hand getMinecraftHand() {
        return this == MainHand ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }

}
