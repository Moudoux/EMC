package me.deftware.client.framework.main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import me.deftware.client.framework.config.Settings;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.resource.ModResourceManager;
import me.deftware.client.framework.util.path.LocationUtil;

import java.io.File;
import java.net.URLClassLoader;

/**
 * This is a parent class for all of the mods loaded by EMC.
 * Your mod must extend this class
 *
 * @author Deftware
 */
public abstract class EMCMod {

	@Deprecated
	public JsonObject modInfo;

	@Getter
	protected ModResourceManager resourceManager;

	@Getter
	protected ModMeta meta;

	public URLClassLoader classLoader;
	private Settings settings;
	public File physicalFile;

	public void init(JsonObject json) {
		modInfo = json;
		meta = new Gson().fromJson(json, ModMeta.class);
		settings = new Settings(meta.getName());
		settings.setupShutdownHook();
		physicalFile = LocationUtil.getClassPhysicalLocation(this.getClass()).toFile();
		try {
			resourceManager = new ModResourceManager(this, "assets");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Bootstrap.logger.debug("Physical jar of {} is {}", meta.getName(), physicalFile.getAbsolutePath());
		initialize();
	}

	/**
	 * Called before any events are sent to your mod, do your initialization here
	 */
	public abstract void initialize();

	/**
	 * Unloads your mod from EMC
	 */
	public void disable() {
		Bootstrap.getMods().remove(meta.getName());
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
	 * Called when Minecraft is closed, use this method to save anything in your mod
	 */
	public void onUnload() { }

	/**
	 * By implementing this function you can call functions in other EMC mods
	 *
	 * @param method The method the caller wants to call
	 * @param caller The EMC mod that is calling your function
	 */
	public void callMethod(String method, String caller, Object object) { }

	/**
	 * Called after Minecraft has been initialized, use this method to display an alternate main menu screen
	 */
	public void postInit() { }

}
