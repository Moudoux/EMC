package io.github.trulyfree.testmods;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.mixin.annotations.ModInfo;

@ModInfo(name = "testmod",
         author = "vtcakavsmoace",
         main = TestEMCMod.class,
         updateLinkOverride = false,
         website = "https://google.com",
         minversion = 13.0,
         version = 1)
public class TestEMCMod extends EMCMod {
    @Override
    public void initialize() {
    }

    @Override
    public EMCClientInfo getClientInfo() {
        return null;
    }

    @Override
    public void onEvent(final Event event) {
    }
}
