package me.deftware.client.framework;

public class FrameworkConstants {

    public static double VERSION = 13.8;
    public static int PATCH = 1;

    public static String AUTHOR = "Deftware";
    public static String FRAMEWORK_NAME = "EMC";
    public static MappingSystem MAPPING_SYSTEM = MappingSystem.Yarn;

	/*
		Mod info
	 */

    public static enum MappingSystem {
        Yarn, MCP
    }

}
