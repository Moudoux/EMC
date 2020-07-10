package me.deftware.client.framework.main;

import me.deftware.client.framework.main.preprocessor.PreProcessorMan;
import me.deftware.client.framework.path.LocationUtil;
import net.fabricmc.loader.launch.knot.KnotClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is only used when running in the fabric environment
 * Minecraft > 1.14
 */
public class Main {

    public static List<String> logging = new ArrayList<>();

    public static void main(String[] args) {
        File emcJar = LocationUtil.getEMC().toFile(), mcDir = LocationUtil.getMinecraftDir().toFile();
        System.setProperty("EMCDir", emcJar != null ? emcJar.getParentFile().getAbsolutePath() : "null");
        System.setProperty("MCDir", mcDir != null ? mcDir.getAbsolutePath() : "null");
        System.setProperty("SUBSYSTEM", "true");
        PreProcessorMan preProcessor = new PreProcessorMan(emcJar);
        try {
            preProcessor.run();
        } catch (Throwable ignored) { }
        for (int i = 0; i < logging.size(); i++) {
            System.setProperty("logging" + i, logging.get(i));
        }
        KnotClient.main(args);
    }

}
