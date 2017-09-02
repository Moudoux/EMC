package me.deftware.client.framework.Values;

/**
 * Multidimensional value map to store game settings. Map ID: 5544
 * 
 * @author deftware
 *
 */
public class SettingsMap extends ValueMap {

	public static final int MapID = 5544;

	public static void setBlockHitDelay(int delay) {
		updateValue(MapID, "block_hit_delay", delay);
	}

	public static int getBlockHitDelay() {
		return getValue(MapID, "block_hit_delay", 5);
	}

	public static void setPickupRange(double range) {
		updateValue(MapID, "block_pickup_range", range);
	}

	public static void setLiquidBoundingBoxAABB(AABBType type) {
		updateValue(MapID, "liquid_aabb", type);
	}

	public static void drawCrosshair(boolean state) {
		updateValue(MapID, "crosshair", state);
	}

	public static enum AABBType {
		NULL_AABB, FULL_BLOCK_AABB
	}

}
