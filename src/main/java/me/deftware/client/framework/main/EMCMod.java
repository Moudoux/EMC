package me.deftware.client.framework.main;

import com.google.gson.JsonObject;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.utils.Settings;

/**
 * This is a parent class for all of the mods loaded by EMC.
 * Your mod must extend this class
 */
public abstract class EMCMod {

	private Settings settings;
	public JsonObject modInfo;

	protected void init(JsonObject json) {
		modInfo = json;
		settings = new Settings();
		settings.initialize(json);
		initialize();
	}

	/**
	 * Called before any events are sent to your mod, do your initialization here
	 */
	public abstract void initialize();

	/**
	 * @return EMCModInfo
	 */
	public abstract EMCModInfo getModInfo();

	/**
	 * Called when EMC has tried to connect to the marketplace API, both successfully and unsuccessfully
	 *
	 * @param status Whether or not EMC has a successful connection with the EMC mod marketplace
	 */
	public void onMarketplaceAuth(boolean status) { }

	/**
	 * Unloads your mod from EMC
	 */
	protected void disable() {
		Bootstrap.getMods().remove(modInfo.get("name").getAsString());
	}

	/**
	 * Returns your main EMC mod settings handler
	 *
	 * @return Settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * Stores all info about an EMC mod
	 */
	@Deprecated
	public static class EMCModInfo {

		private String modName, modVersion;

		public EMCModInfo(String modName, String modVersion) {
			this.modName = modName;
			this.modVersion = modVersion;
		}

		public String getModName() {
			return modName;
		}

		public String getModVersion() {
			return modVersion;
		}

	}

	/**
	 * Called when Minecraft is closed, use this method to save anything in your mod
	 */
	public void onUnload() {
	}

	/**
	 * By implementing this function you can call functions in other EMC mods
	 *
	 * @param method The method the caller wants to call
	 * @param caller The EMC mod that is calling your function
	 */
	public void callMethod(String method, String caller) {
	}

}
