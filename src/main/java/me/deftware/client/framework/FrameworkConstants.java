package me.deftware.client.framework;

public class FrameworkConstants {

    public static double VERSION = 13.9;
    public static int PATCH = 6;

    public static int SCHEME = 2;
    public static String AUTHOR = "Deftware";
    public static MappingsLoader MAPPING_LOADER = MappingsLoader.Fabric;
    public static MappingSystem MAPPING_SYSTEM = MappingSystem.Yarn;

    public static String toDataString() {
        return String.format("EMC v%s version %s.%s by %s using %s with %s mappings", SCHEME, VERSION, PATCH, AUTHOR, MAPPING_LOADER.name(), MAPPING_SYSTEM.name());
    }

    public enum MappingSystem {
        Yarn, MCPConfig, MOJANG
    }

    public enum MappingsLoader {
        Fabric, MCP
    }

}
