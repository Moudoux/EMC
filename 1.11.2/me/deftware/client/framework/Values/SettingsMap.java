package me.deftware.client.framework.Values;

/**
 * Multidimensional value map to store game settings. Map ID: 5544
 * 
 * @author deftware
 *
 */
public class SettingsMap extends ValueMap {

	private static final int MapID = 5544;
	
	public static void setBlockHitDelay(int delay) {
		updateValue(MapID, "block_hit_delay", delay);
	}
	
	public static int getBlockHitDelay() {
		return getValue(MapID, "block_hit_delay", 5);
	}

}
