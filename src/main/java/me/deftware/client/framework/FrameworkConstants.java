package me.deftware.client.framework;

public class FrameworkConstants {

    public static double VERSION = 13.9;
    public static int PATCH = 5;

    public static int SCHEME = 2;
    public static String AUTHOR = "Deftware";
    public static String FRAMEWORK_NAME = "EMC-F v2";
    public static MappingsLoader MAPPING_LOADER = MappingsLoader.Fabric;
    public static MappingSystem MAPPING_SYSTEM = MappingSystem.Yarn;

    public enum MappingSystem {
        Yarn, MCPConfig, MOJANG
    }

    public enum MappingsLoader {
        Fabric, MCP
    }

}
