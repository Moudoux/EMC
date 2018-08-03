package me.deftware.client.framework.utils;

import com.google.gson.*;
import me.deftware.client.framework.main.Bootstrap;

import java.awt.*;
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
			String modName = "EMC";
			if (Bootstrap.modsInfo != null) {
				modName = modInfo.get("name").getAsString();
			}
			String file = OSUtils.getMCDir() + "EMC_Configs" + File.separator + modName + "_config.json";
			configFile = new File(file);
			if (!configFile.exists()) {
				configFile.createNewFile();
				config = new Gson().fromJson("{}", JsonObject.class);
				addNode("version", "3.0");
				saveConfig();
			} else {
				config = new Gson().fromJson(getConfigFileContents(), JsonObject.class);
			}
			Runtime.getRuntime().addShutdownHook(new Thread(() -> saveConfig()));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Failed to load mod config, resetting it..");
			if (configFile != null) {
				if (configFile.exists()) {
					configFile.delete();
				}
			}
			initialize(modInfo);
		}
	}

	public synchronized ArrayList<String> getArrayList(String node) {
		ArrayList<String> array = new ArrayList<>();
		try {
			JsonObject jsonObject = new Gson().fromJson(getConfigFileContents(), JsonObject.class);
			if (!jsonObject.has(node)) {
				return array;
			}
			JsonArray arr = ((JsonArray) jsonObject.get(node)).getAsJsonArray();
			for (JsonElement s : arr) {
				array.add(s.getAsString());
			}
		} catch (Exception ex) {
			array.clear();
		}
		return array;
	}

	public synchronized void addArrayList(String node, ArrayList<String> array) {
		JsonArray jsonArray = new JsonArray();
		for (String s : array) {
			jsonArray.add(new JsonPrimitive(s));
		}
		config.add(node, jsonArray);
	}

	public synchronized Color getColor(String node, Color _default) {
		if (getNode(node, "").equals("")) {
			return _default;
		}
		try {
			// r,g,b,a
			String[] c = getNode(node).split(",");
			return new Color(Integer.valueOf(c[0]), Integer.valueOf(c[1]), Integer.valueOf(c[2]),
					Integer.valueOf(c[3]));
		} catch (Exception ex) {
			return _default;
		}
	}

	public synchronized void saveColor(String node, Color color) {
		try {
			// r,g,b,a
			addNode(node, color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + color.getAlpha());
		} catch (Exception ex) {
		}
	}

	public synchronized void addArrayListValue(String node, String value) {
		try {
			ArrayList<String> array = getArrayList(node);
			if (!array.contains(value)) {
				array.add(value);
			}
			deleteNode(node);
			addArrayList(node, array);
		} catch (Exception ex) {
		}
	}

	public synchronized void removeArrayListValue(String node, String value) {
		try {
			ArrayList<String> array = getArrayList(node);
			if (array.contains(value)) {
				array.remove(value);
			}
			deleteNode(node);
			addArrayList(node, array);
		} catch (Exception ex) {
		}
	}

	public synchronized void addNode(String node, String value) {
		if (config.has(node)) {
			config.remove(node);
		}
		config.add(node, new JsonPrimitive(value));
	}

	public synchronized void saveBool(String node, boolean value) {
		addNode(node, String.valueOf(value));
	}

	public synchronized void saveInt(String node, int value) {
		addNode(node, String.valueOf(value));
	}

	public synchronized void saveFloat(String node, float value) {
		addNode(node, String.valueOf(value));
	}

	public synchronized void saveDouble(String node, double value) {
		addNode(node, String.valueOf(value));
	}

	public synchronized boolean getBool(String node, boolean _default) {
		return Boolean.valueOf(getNode(node, String.valueOf(_default)));
	}

	public synchronized float getFloat(String node, float _default) {
		return Float.valueOf(getNode(node, String.valueOf(_default)));
	}

	public synchronized int getInt(String node, int _default) {
		return Integer.valueOf(getNode(node, String.valueOf(_default)));
	}

	public synchronized double getDouble(String node, double _default) {
		return Double.valueOf(getNode(node, String.valueOf(_default)));
	}

	public synchronized String getNode(String node) {
		return getNode(node, "");
	}

	public synchronized String getNode(String node, String defaultStr) {
		try {
			return config.has(node) ? config.get(node).getAsString() : defaultStr;
		} catch (Exception ex) {
			System.err.println("Failed to read config value");
			ex.printStackTrace();
			return defaultStr;
		}
	}

	public synchronized boolean hasNode(String node) {
		return config.has(node);
	}

	public synchronized void deleteNode(String node) {
		if (config.has(node)) {
			config.remove(node);
		}
	}

	public synchronized String getConfigFileContents() throws IOException {
		String output = "";
		for (String s : Files.readAllLines(Paths.get(configFile.getAbsolutePath()), StandardCharsets.UTF_8)) {
			output += s;
		}
		return output;
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
