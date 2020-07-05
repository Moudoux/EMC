package me.deftware.client.framework.main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.deftware.client.framework.main.preprocessor.PreProcessorMan;
import me.deftware.client.framework.path.LocationUtil;
import me.deftware.client.framework.utils.HashUtils;
import me.deftware.client.framework.utils.WebUtils;
import net.fabricmc.loader.launch.knot.KnotClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is only used when running in the fabric environment
 * Minecraft > 1.14
 */
public class Main {

    private static PreProcessorMan preProcessor;
    public static List<String> logging = new ArrayList<>();

    public static void main(String[] args) {
        File emcJar = LocationUtil.getEMC().toFile(), mcDir = LocationUtil.getMinecraftDir().toFile();
        System.setProperty("EMCDir", emcJar != null ? emcJar.getParentFile().getAbsolutePath() : "null");
        System.setProperty("MCDir", mcDir != null ? mcDir.getAbsolutePath() : "null");
        if (emcJar != null && mcDir != null) {
            validateOptiFine(emcJar.getParentFile().getAbsolutePath(), mcDir.getAbsolutePath());
            validateFilesSizes(emcJar.getParentFile());
        }
        System.setProperty("SUBSYSTEM", "true");
        preProcessor = new PreProcessorMan(emcJar);
        try {
            preProcessor.run();
        } catch (Throwable ignored) { }
        for (int i = 0; i < logging.size(); i++) {
            System.setProperty("logging" + i, logging.get(i));
        }
        KnotClient.main(args);
    }

    /**
     * Validates that files are not empty prior to launching
     */
    private static void validateFilesSizes(File path) {
        File[] files = path.listFiles();
        if (files == null) return;
        for (File file : files) {
            try {
                if (file.exists()) {
                    if (file.length() == 0) {
                        logging.add("Removed invalid fabric mod " + file.getName());
                        if (!file.delete()) {
                            // cant really do much now
                        }
                    }
                }
            } catch (Throwable ignored) {}
        }
    }

    private static void validateOptiFine(String emcPath, String mcPath) {
       try {
           if (emcPath.contains("latest-")) {
               String dataUrl = "https://gitlab.com/EMC-Framework/maven/raw/master/marketplace/plugins/optifine/versions.json";
               String mcVersion = emcPath.split("latest-")[1];
               File optifabricJar = new File(emcPath + File.separator + "optifabric.jar");
               File optifineModJar = new File(mcPath + File.separator + "mods" + File.separator + "optifine.jar");
               JsonObject json = new Gson().fromJson(WebUtils.get(dataUrl), JsonObject.class);
               if (json.has(mcVersion)) {
                   JsonObject data = json.get(mcVersion).getAsJsonObject();
                   if (data.get("available").getAsBoolean()) {
                       if (optifineModJar.exists()) {
                           if (!data.get("sha1").getAsString().equalsIgnoreCase(HashUtils.getSha1(optifineModJar))) {
                               optifineModJar.delete();
                               if (optifabricJar.exists()) {
                                   optifabricJar.delete();
                               }
                           }
                       } else if (optifabricJar.exists()) {
                           optifabricJar.delete();
                       }
                   }
               }
           }
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }

}
