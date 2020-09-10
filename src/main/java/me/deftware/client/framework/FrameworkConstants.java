package me.deftware.client.framework;

import me.deftware.client.framework.minecraft.Minecraft;

/**
 * @author Deftware
 */
public class FrameworkConstants {

    public static double VERSION = 16.0;
    public static int PATCH = 0, SCHEME = 4;

    public static boolean VALID_EMC_INSTANCE = false, SUBSYSTEM_IN_USE = false, OPTIFINE = false;
    public static String FRAMEWORK_MAVEN_URL = "https://gitlab.com/EMC-Framework/maven/raw/master/";
    public static MappingsLoader MAPPING_LOADER = MappingsLoader.Fabric;
    public static MappingSystem MAPPING_SYSTEM = MappingSystem.YarnV2;

    public static String toDataString() {
        return String.format("EMC v%s version %s.%s using %s with %s mappings", SCHEME, VERSION, PATCH, MAPPING_LOADER.name(), MAPPING_SYSTEM.name());
    }

    public static String getFrameworkMaven() {
        String mavenName = "me.deftware:EMC";
        if (MAPPING_LOADER == MappingsLoader.Forge) {
            mavenName += "-Forge";
        } else if (MAPPING_LOADER == MappingsLoader.Fabric) {
            mavenName += "-F";
            if (MAPPING_SYSTEM == MappingSystem.YarnV2) {
                mavenName += "-v2";
            }
        }
        return mavenName + ":latest-" + Minecraft.getMinecraftVersion();
    }

    public enum MappingSystem {
        Yarn, YarnV2, MCPConfig
    }

    public enum MappingsLoader {
        Fabric, Tweaker, Forge
    }

}
