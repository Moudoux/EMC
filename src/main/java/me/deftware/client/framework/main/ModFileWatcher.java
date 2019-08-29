package me.deftware.client.framework.main;

import java.io.File;
import java.util.TimerTask;

public class ModFileWatcher extends TimerTask {
    ModFileWatcher(File file) {
        System.out.println("Initialized FileWatcher for EMC Mod: " + file.getAbsolutePath());
    }

    @Override
    public void run() {

    }
}
