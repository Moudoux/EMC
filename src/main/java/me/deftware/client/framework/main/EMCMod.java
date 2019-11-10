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
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * This is a parent class for all of the mods loaded by EMC.
 * Your mod must extend this class
 */
public abstract class EMCMod {

	private Settings settings;
	public JsonObject modInfo;
	private static String manualJsonLocation;
	private static boolean isFileDialogOpen = false;

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
	 * Helper Method.
	 *
	 * @return String version
	 */
	public String getEMCVersion() {
		return FrameworkConstants.VERSION + "." + FrameworkConstants.PATCH;
	}

	/**
	 * Manually Select an EMC Json File
	 * Useful if unable to locate automatically by other means
	 *
	 * @return A Valid EMC Json File
	 */
	public static File getEMCJsonFile() {
		String defaultJsonName = OSUtils.getRunningFolder() + OSUtils.getVersion() + ".json";
		File jsonFile = new File(Bootstrap.EMCSettings != null ? Bootstrap.EMCSettings.getString("EMC_JSON_LOCATION", defaultJsonName) : defaultJsonName);

		if (!jsonFile.exists()) {
			if (manualJsonLocation != null && new File(manualJsonLocation).exists()) {
				return new File(manualJsonLocation);
			} else {
				System.out.println("Opening File Open Dialog, as JSON Cannot be found...");

				try {
					JFXPanel frame = new JFXPanel(); // Initialize JavaFX Environment
					isFileDialogOpen = true;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							FileChooser fileChooser = new FileChooser();
							fileChooser.setTitle("Open EMC Json File (Required)");
							fileChooser.getExtensionFilters().addAll(
									new FileChooser.ExtensionFilter("Json", "*.json")
							);

							File resultFile = fileChooser.showOpenDialog(null);

							if (resultFile != null) {
								manualJsonLocation = resultFile.getAbsolutePath();
							} else {
								System.out.println("JSON not found, things will break if other addons are using this!");
							}
							isFileDialogOpen = false;
						}
					});
				} catch (Exception | Error ex) {
					System.out.println("Error: EMC Json File Open Dialog failed to open, please Input your EMC Json Location manually in your EMC Config @ emcJsonLocation");
					ex.printStackTrace();
				}
			}
		} else {
			return jsonFile;
		}

		while (isFileDialogOpen) {
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Save as New Json Location if Settings Available
		if (Bootstrap.EMCSettings != null) {
			Bootstrap.EMCSettings.saveString("EMC_JSON_LOCATION", manualJsonLocation);
			Bootstrap.EMCSettings.saveConfig();
		}
		return new File(manualJsonLocation);
	}

	/**
	 * Search for a specified JsonElement within a Json File
	 *
	 * @param jsonFile Json File Data
	 * @param searchTarget Element Name to look for
	 *
	 * @return The Matching JsonElement found in the Json File
	 */
	public static JsonElement lookupElementInJson(File jsonFile, String searchTarget) {
		try {
			return lookupElementInJson(FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8), searchTarget);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Retrieves Json Data as a JsonObject
	 *
	 * @param jsonFile Json File Location
	 * @return a JsonObject derived from Json Data
	 */
	public static JsonObject getJsonDataAsObject(File jsonFile) {
		try {
			return getJsonDataAsObject(FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8));
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Retrieves Json Data as a JsonObject
	 *
	 * @param jsonData Json Data
	 * @return a JsonObject derived from Json Data
	 */
	public static JsonObject getJsonDataAsObject(String jsonData) {
		return new Gson().fromJson(jsonData, JsonObject.class);
	}

	/**
	 * Search for a specified JsonElement within a Json File
	 *
	 * @param jsonFileData Json File Data
	 * @param searchTarget Element Name to look for
	 *
	 * @return The Matching JsonElement found in the Json File
	 */
	public static JsonElement lookupElementInJson(String jsonFileData, String searchTarget) {
		JsonElement resultingElement = null;
		JsonObject jsonData;

		try {
			if (!jsonFileData.isEmpty()) {
				jsonData = getJsonDataAsObject(jsonFileData);

				if (jsonData != null) {
					Set<Map.Entry<String, JsonElement>> entries = jsonData.entrySet();
					for (Map.Entry<String, JsonElement> entry: entries) {
						if (entry.getKey().contains(searchTarget)) {
							resultingElement = entry.getValue();
						}
					}
				} else {
					System.out.println("Json Data returned null, looking for " + searchTarget);
				}
			} else {
				System.out.println("Json File Data is invalid, please correct your parameters!");
			}
		} catch (Exception ex) {
			System.out.println("Failed to lookup " + searchTarget + " in json file...");
			ex.printStackTrace();;
		}

		return resultingElement;
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
