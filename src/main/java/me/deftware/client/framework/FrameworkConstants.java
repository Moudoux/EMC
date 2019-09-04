package me.deftware.client.framework;

public class FrameworkConstants {

    public static double VERSION = 13.9;
    public static int PATCH = 3;

    public static String AUTHOR = "Deftware";
    public static String FRAMEWORK_NAME = "EMC";
    public static MappingSystem MAPPING_SYSTEM = MappingSystem.Yarn;

    public static enum MappingSystem {
        Yarn, MCP, MOJANG
    }

}
