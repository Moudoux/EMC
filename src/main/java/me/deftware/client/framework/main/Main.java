package me.deftware.client.framework.main;

import me.deftware.client.framework.path.LocationUtil;
import net.fabricmc.loader.launch.knot.KnotClient;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File emcJar = LocationUtil.getEMC().toFile(), mcDir = LocationUtil.getMinecraftDir().toFile();
        System.setProperty("EMCDir", emcJar != null ? emcJar.getParentFile().getAbsolutePath() : "null");
        System.setProperty("MCDir", mcDir != null ? mcDir.getAbsolutePath() : "null");
        KnotClient.main(args);
    }

}
