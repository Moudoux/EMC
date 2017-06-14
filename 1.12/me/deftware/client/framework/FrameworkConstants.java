package me.deftware.client.framework;

public class FrameworkConstants {

	public static final int VERSION = 3;
	public static final String AUTHOR = "Deftware";
	public static final String FRAMEWORK_NAME = "EMC";
	
	// The Minecraft protocol version
	public static int PROTOCOL_VERSION = 335;
	public static String MC_VERSION = "1.12";

	/**
	 * Change what protocol Minecraft uses with one call
	 * 
	 * @param name
	 * @param protocol
	 */
	public static void setProtocolVersion(String name, int protocol) {
		PROTOCOL_VERSION = protocol;
		MC_VERSION = name;
	}

}
