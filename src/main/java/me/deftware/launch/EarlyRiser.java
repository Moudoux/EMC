package me.deftware.launch;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.spongepowered.asm.mixin.Mixins;

public class EarlyRiser implements Runnable {

    @Override
    public void run() {
        Mixins.addConfiguration("emc.client.json");
        for (ModContainer modContainer : FabricLoader.getInstance().getAllMods()) {
            if (modContainer.getMetadata().getName().equals("Optifabric")) {
                Mixins.addConfiguration("optifine.client.json");
                break;
            }
        }
    }

}
