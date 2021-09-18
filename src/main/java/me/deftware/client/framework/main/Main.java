package me.deftware.client.framework.main;

import me.deftware.client.framework.main.subsystem.FabricModifier;
import me.deftware.client.framework.main.preprocessor.PreProcessorMan;
import me.deftware.client.framework.util.path.LocationUtil;
import net.fabricmc.loader.launch.knot.KnotClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is only used when running in the fabric environment
 * Minecraft > 1.14
 *
 * @author Deftware
 */
public class Main {

    public static List<String> logging = new ArrayList<>();

    public static void main(String[] args) {
        preprocess(args);
        try {
            new FabricModifier().run();
        } catch (Throwable e) {
            throw new RuntimeException("Unable to replace Fabric loader", e);
        }
        KnotClient.main(args);
    }

    @Deprecated
    private static void preprocess(String[] args) {
        File runDir = null, emcJar = LocationUtil.getEMC().toFile();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--gameDir")) {
                runDir = new File(args[i + 1]);
                break;
            }
        }
        if (runDir != null && emcJar != null) {
            System.setProperty("MCDir", runDir.getAbsolutePath());
            System.setProperty("EMCDir", emcJar.getParentFile().getAbsolutePath());
            PreProcessorMan preProcessor = new PreProcessorMan(runDir, emcJar);
            try {
                preProcessor.run();
            } catch (Throwable ignored) { }
            for (int i = 0; i < logging.size(); i++) {
                System.setProperty("logging" + i, logging.get(i));
            }
        } else {
            System.setProperty("logging0", "Failed to locate Minecraft runDir or EMC jar");
        }
        System.setProperty("SUBSYSTEM", "true");
    }

}
