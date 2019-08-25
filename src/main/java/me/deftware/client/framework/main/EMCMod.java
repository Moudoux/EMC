package me.deftware.client.framework.main;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.utils.OSUtils;
import me.deftware.client.framework.utils.Settings;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This is a parent class for all of the mods loaded by EMC.
 * Your mod must extend this class
 */
public abstract class EMCMod {

	private Settings settings;
	public JsonObject modInfo;
	private String manualJsonLocation;

	public void init(JsonObject json) {
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
	 * Unloads your mod from EMC
	 */
	public void disable() {
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
	 * Returns your current EMC Version from FrameworkConstants
	 *
	 * PLEASE USE THIS INSTEAD OF YOUR OWN METHOD FOR COMPATIBILITY SAKES
	 * NOT DOING SO WILL BREAK YOUR MOD ON OTHER LAUNCHERS
	 *
	 * @return String version
	 */
	public String getEMCVersion() {
		return FrameworkConstants.VERSION + "." + FrameworkConstants.PATCH;
	}

	/**
	 * Manually Select an EMC Json File
	 * Used if unable to locate automatically
	 *
	 * @return A Valid EMC Json File
	 */
	public File getEMCJsonFile() {
		File jsonFile = new File(OSUtils.getRunningFolder() + OSUtils.getVersion() + ".json");
		String version = null;
		if (!jsonFile.exists()) {
			if (manualJsonLocation != null && new File(manualJsonLocation).exists()) {
				return new File(manualJsonLocation);
			} else {
				System.out.println("Opening File Open Dialog, as JSON Cannot be found");

				new JFXPanel(); // Initialize JavaFX Environment
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						FileChooser fileChooser = new FileChooser();
						fileChooser.setTitle("Open EMC Json File");
						fileChooser.setInitialDirectory(new File(OSUtils.getRunningFolder()));
						fileChooser.getExtensionFilters().addAll(
								new FileChooser.ExtensionFilter("Json", "*.json")
						);

						File resultFile = fileChooser.showOpenDialog(null);

						if (resultFile != null) {
							manualJsonLocation = resultFile.getAbsolutePath();
						} else {
							System.out.println("JSON not found, things will break ith other addons using this!");
						}
					}
				});

				return new File(manualJsonLocation);
			}
		} else {
			return jsonFile;
		}
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
