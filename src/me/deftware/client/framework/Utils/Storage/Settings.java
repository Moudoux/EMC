package me.deftware.client.framework.Utils.Storage;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import me.deftware.client.framework.Main.FrameworkLoader;
import me.deftware.client.framework.Utils.OSUtils;

public class Settings {

	private File configFile;
	private JsonObject config;

	public synchronized void initialize(JsonObject clientInfo) {
		try {
			String clientName = "EMC";
			if (FrameworkLoader.modsInfo != null) {
				clientName = clientInfo.get("name").getAsString();
			}
			String file = OSUtils.getMCDir() + clientName + "_Config.json";
			configFile = new File(file);
			if (!configFile.exists()) {
				configFile.createNewFile();
				addNode("version", "1.0");
				saveConfig();
			} else {
				config = new Gson().fromJson(getConfigFileContents(), JsonObject.class);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
		Runtime.getRuntime().addShutdownHook(new Thread(() -> saveConfig()));
	}

	public synchronized ArrayList<String> getArrayList(String node) {
		ArrayList<String> array = new ArrayList<String>();
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
			;
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
			;
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
			;
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
		return config.has(node) ? config.get(node).getAsString() : defaultStr;
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
