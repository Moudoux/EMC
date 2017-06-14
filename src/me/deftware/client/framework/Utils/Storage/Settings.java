package me.deftware.client.framework.Utils.Storage;

import java.awt.Color;
import java.io.File;
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

import net.minecraft.client.Minecraft;

/**
 * Client settings
 * 
 * @author deftware
 *
 */
public class Settings {

	private String configPath;

	/**
	 * Initialize the config file
	 */
	public synchronized void initialize() {
		try {
			File minecraft = new File(Minecraft.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			File configFile = new File(minecraft.getParent() + File.separator + "Client_Config.json");
			this.configPath = configFile.getAbsolutePath();
			if (!configFile.exists()) {
				try {
					configFile.createNewFile();
					flushConfig("{}");
					addNode("version", "1.0");
				} catch (Exception e) {
					;
				}
			}
		} catch (Exception ex) {
			;
		}
	}

	/**
	 * Returns a ArrayList<String> for a given node
	 * 
	 * @param node
	 * @return ArrayList<String>
	 */
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

	/**
	 * Put's a given ArrayList<String> into a node in the config file
	 * 
	 * @param node
	 * @param array
	 */
	@SuppressWarnings("unchecked")
	public synchronized void addArrayList(String node, ArrayList<String> array) {
		try {
			JsonObject jsonObject = new Gson().fromJson(getConfigFileContents(), JsonObject.class);
			JsonArray jsonArray = new JsonArray();
			for (String s : array) {
				jsonArray.add(new JsonPrimitive(s));
			}
			jsonObject.add(node, jsonArray);
			flushConfig(jsonObject.toString());
		} catch (Exception ex) {
			;
		}
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

	/**
	 * Adds a value to a ArrayList<String> node, where node is a ArrayList and
	 * value the value you want to add
	 * 
	 * @param node
	 * @param value
	 */
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

	/**
	 * Removes a value from a ArrayList<String> node, where node is a ArrayList
	 * and value the value you want to add
	 * 
	 * @param node
	 * @param value
	 */
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

	/**
	 * Set's a node's value, will overwrite value if it already exists
	 * 
	 * @param node
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public synchronized void addNode(String node, String value) {
		try {
			JsonObject jsonObject = new Gson().fromJson(getConfigFileContents(), JsonObject.class);
			if (jsonObject.has(node)) {
				jsonObject.remove(node);
			}
			jsonObject.add(node, new JsonPrimitive(value));
			flushConfig(jsonObject.toString());
		} catch (Exception ex) {
			;
		}
	}
	
	public synchronized void saveBool(String node, boolean value) {
		try {
			addNode(node,String.valueOf(value));
		} catch (Exception ex) {
			;
		}
	} 
	
	public synchronized void saveInt(String node, int value) {
		try {
			addNode(node,String.valueOf(value));
		} catch (Exception ex) {
			;
		}
	} 
	
	public synchronized void saveDouble(String node, double value) {
		try {
			addNode(node, String.valueOf(value));
		} catch (Exception ex) {
			;
		}
	}

	public synchronized boolean getBool(String node, boolean _default) {
		String data = getNode(node,"");
		if (data.equals("")) {
			return _default;
		}
		try {
			return Boolean.valueOf(data);
		} catch (Exception ex) {
			return _default;
		}
	}
	
	public synchronized float getFloat(String node, float _default) {
		String data = getNode(node, "");
		if (data.equals("")) {
			return _default;
		}
		try {
			return Float.valueOf(data);
		} catch (Exception ex) {
			return _default;
		}
	}

	public synchronized int getInt(String node, int _default) {
		String data = getNode(node,"");
		if (data.equals("")) {
			return _default;
		}
		try {
			return Integer.valueOf(data);
		} catch (Exception ex) {
			return _default;
		}
	}

	public synchronized double getDouble(String node, double _default) {
		String data = getNode(node, "");
		if (data.equals("")) {
			return _default;
		}
		try {
			return Double.valueOf(data);
		} catch (Exception ex) {
			return _default;
		}
	}

	/**
	 * Returns the value of a given node
	 * 
	 * @param node
	 * @return String
	 */
	public synchronized String getNode(String node) {
		return getNode(node, "");
	}

	/**
	 * Returns the value of a given node
	 * 
	 * @param node
	 * @return String
	 */
	public synchronized String getNode(String node, String defaultr) {
		try {
			JsonObject jsonObject = new Gson().fromJson(getConfigFileContents(), JsonObject.class);
			if (!jsonObject.has(node)) {
				return defaultr;
			}
			return jsonObject.get(node).getAsString();
		} catch (Exception ex) {
			;
			return defaultr;
		}
	}

	/**
	 * Check if the config has a given node
	 * 
	 * @param node
	 * @return Boolean
	 */
	public synchronized boolean hasNode(String node) {
		try {
			JsonObject jsonObject = new Gson().fromJson(getConfigFileContents(), JsonObject.class);
			if (!jsonObject.has(node)) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			;
			return false;
		}
	}

	/**
	 * Delete's a node
	 * 
	 * @param node
	 */
	public synchronized void deleteNode(String node) {
		try {
			JsonObject jsonObject = new Gson().fromJson(getConfigFileContents(), JsonObject.class);
			if (jsonObject.has(node)) {
				jsonObject.remove(node);
			}
			flushConfig(jsonObject.toString());
		} catch (Exception ex) {
			;
		}
	}

	/**
	 * Returns config file's JSON content
	 * 
	 * @return String
	 */
	public synchronized String getConfigFileContents() {
		try {
			String output = "";
			for (String s : Files.readAllLines(Paths.get(configPath), StandardCharsets.UTF_8)) {
				output += s;
			}
			return output;
		} catch (Exception ex) {
			;
			return "";
		}
	}

	/**
	 * Saves the config
	 * 
	 * @param jsonContent
	 */
	public synchronized void flushConfig(String jsonContent) {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(jsonContent);
			jsonContent = gson.toJson(je);
			PrintWriter writer = new PrintWriter(configPath, "UTF-8");
			writer.println(jsonContent);
			writer.close();
		} catch (Exception ex) {
			;
		}
	}

}
