package me.deftware.client.framework.utils;

import com.google.gson.*;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.path.OSUtils;
import me.deftware.client.framework.wrappers.IMinecraft;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Config handler for mods
 */
public class Settings {

	private File configFile;
	private JsonObject config;

	public synchronized void initialize(JsonObject modInfo) {
		try {
			String modName = (Bootstrap.modsInfo != null && modInfo != null) ?  modInfo.get("name").getAsString() : "EMC";
			String file = OSUtils.getMCDir() + "libraries" + File.separator + "EMC" + File.separator + IMinecraft.getMinecraftVersion() + File.separator + "configs" + File.separator + modName + "_config.json";
			configFile = new File(file);
			if (!configFile.exists()) {
				if (!configFile.createNewFile()) {
					Bootstrap.logger.error("Failed to create config");
				}
				config = new Gson().fromJson("{}", JsonObject.class);
				saveDouble("version", 3.1);
				saveConfig();
			} else {
				config = new Gson().fromJson(getConfigFileContents(), JsonObject.class);
			}
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
					Bootstrap.logger.info("Saving EMC config for mod " + modName);
					saveConfig();
			}));
		} catch (Exception ex) {
			ex.printStackTrace();
			Bootstrap.logger.warn("Failed to load mod config, resetting it..");
			if (configFile != null) {
				if (configFile.exists()) {
					if (!configFile.delete()) {
						Bootstrap.logger.warn("Failed to delete config");
					}
				}
			}
			initialize(modInfo);
		}
	}

	public synchronized void addNode(String node, JsonPrimitive value) {
		if (config.has(node)) {
			config.remove(node);
		}
		config.add(node, value);
	}

	public void saveBool(String node, boolean value) {
		addNode(node, new JsonPrimitive(value));
	}

	public void saveInt(String node, int value) {
		addNode(node, new JsonPrimitive(value));
	}

	public void saveFloat(String node, float value) {
		addNode(node, new JsonPrimitive(value));
	}

	public void saveDouble(String node, double value) {
		addNode(node, new JsonPrimitive(value));
	}

	public void saveString(String node, String value) {
		addNode(node, new JsonPrimitive(value));
	}

	public boolean getBool(String node, boolean _default) {
		if (config.has(node)) {
			return config.get(node).getAsBoolean();
		}
		return _default;
	}

	public float getFloat(String node, float _default) {
		if (config.has(node)) {
			return config.get(node).getAsFloat();
		}
		return _default;
	}

	public int getInt(String node, int _default) {
		if (config.has(node)) {
			return config.get(node).getAsInt();
		}
		return _default;
	}

	public double getDouble(String node, double _default) {
		if (config.has(node)) {
			return config.get(node).getAsDouble();
		}
		return _default;
	}

	public String getString(String node, String _default) {
		if (config.has(node)) {
			return config.get(node).getAsString();
		}
		return _default;
	}

	public JsonArray getArray(String node) {
		return config.get(node).getAsJsonArray();
	}

	public int getArrayIndex(String node, JsonPrimitive needle) {
		int index = 0;
		for (JsonElement e : config.get(node).getAsJsonArray()) {
			if (e.getAsJsonPrimitive().equals(needle)) {
				break;
			}
			index++;
		}
		return index;
	}

	public void addArray(String node, ArrayList<JsonElement> array) {
		JsonArray arr = new JsonArray();
		array.forEach(arr::add);
		config.add(node, arr);
	}

	public void deleteNode(String node) {
		if (config.has(node)) {
			config.remove(node);
		}
	}

	public boolean hasNode(String node) {
		return config.has(node);
	}

	public JsonObject getConfig() {
		return config;
	}

	public synchronized String getConfigFileContents() throws IOException {
		StringBuilder output = new StringBuilder();
		for (String s : Files.readAllLines(Paths.get(configFile.getAbsolutePath()), StandardCharsets.UTF_8)) {
			output.append(s);
		}
		return output.toString();
	}

	public synchronized void saveConfig() {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(config.toString());
			String jsonContent = gson.toJson(je);
			PrintWriter writer = new PrintWriter(configFile.getAbsolutePath(), "UTF-8");
			writer.println(jsonContent);
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
