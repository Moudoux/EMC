package me.deftware.client.framework;

import me.deftware.client.framework.wrappers.IMinecraft;

import java.util.ArrayList;
import java.util.List;

public class FrameworkConstants {

    public static double VERSION = 14.0;
    public static int PATCH = 2, SCHEME = 3;

    public static boolean VALID_EMC_INSTANCE = false, SUBSYSTEM_IN_USE = false;
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
        return mavenName + ":latest-" + IMinecraft.getMinecraftVersion();
    }

    public enum MappingSystem {
        Yarn, YarnV2, MCPConfig
    }

    public enum MappingsLoader {
        Fabric, Tweaker, Forge
    }

}
