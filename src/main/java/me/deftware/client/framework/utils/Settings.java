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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * Simple Json based config for EMC mods
 *
 * @author Deftware
 */
public class Settings {

	private final JsonObject config;
	private final File configFile;
	private final Queue<Consumer<Void>> shutdownQueue = new ConcurrentLinkedQueue<>();

	public Settings(String modName) {
		configFile = new File(String.format("%s/libraries/EMC/%s/configs/%s_config.json", OSUtils.getMCDir(), IMinecraft.getMinecraftVersion(), modName));
		JsonObject jsonObject;
		try {
			// Load config from file
			jsonObject = configFile.exists() ?
					new Gson().fromJson(getConfigFileContents(), JsonObject.class) : createConfig(configFile);
		} catch (Exception ex) {
			ex.printStackTrace();
			Bootstrap.logger.error("Failed to load config for {}, resetting it...", modName);
			// Delete old config file
			if (configFile.exists() && !configFile.delete()) {
				Bootstrap.logger.error("Failed to delete {}", configFile.getName());
			}
			// Create new
			jsonObject = createConfig(configFile);
		}
		config = jsonObject;
	}

	private JsonObject createConfig(File file) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("version", 4.0);
		try {
			if (!configFile.exists()) {
				// Create a new file
				if (!configFile.createNewFile()) {
					throw new Exception("Failed to create config file for " + file.getName());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Bootstrap.logger.error("Failed to create config {}", file.getName());
		}
		return jsonObject;
	}

	public void setupShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Thread.currentThread().setName(configFile.getName() + " config shutdown thread");
			Bootstrap.logger.info("Saving {}", configFile.getName());
			for (Consumer<Void> consumer : shutdownQueue) {
				consumer.accept(null);
			}
			save();
		}));
	}

	public File getConfigFile() {
		return configFile;
	}

	public Queue<Consumer<Void>> getShutdownQueue() {
		return shutdownQueue;
	}

	private synchronized String getConfigFileContents() throws IOException {
		return String.join("\n", Files.readAllLines(Paths.get(configFile.getAbsolutePath()), StandardCharsets.UTF_8));
	}

	public synchronized void save() {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(config.toString());
			String jsonContent = gson.toJson(je);
			PrintWriter writer = new PrintWriter(configFile.getAbsolutePath(), "UTF-8");
			writer.println(jsonContent);
			writer.close();
		} catch (Exception ex) {
			Bootstrap.logger.error("Failed to save config", ex);
		}
	}

	public synchronized boolean hasKey(String key) {
		return config.has(key);
	}

	/*
		Primitives
	 */

	// Getters

	public synchronized int getPrimitive(String key, int defaultValue) {
		return config.has(key) ? config.getAsJsonPrimitive(key).getAsInt() : defaultValue;
	}

	public synchronized float getPrimitive(String key, float defaultValue) {
		return config.has(key) ? config.getAsJsonPrimitive(key).getAsFloat() : defaultValue;
	}

	public synchronized double getPrimitive(String key, double defaultValue) {
		return config.has(key) ? config.getAsJsonPrimitive(key).getAsDouble() : defaultValue;
	}

	public synchronized long getPrimitive(String key, long defaultValue) {
		return config.has(key) ? config.getAsJsonPrimitive(key).getAsLong() : defaultValue;
	}

	public synchronized short getPrimitive(String key, short defaultValue) {
		return config.has(key) ? config.getAsJsonPrimitive(key).getAsShort() : defaultValue;
	}

	public synchronized byte getPrimitive(String key, byte defaultValue) {
		return config.has(key) ? config.getAsJsonPrimitive(key).getAsByte() : defaultValue;
	}

	public synchronized boolean getPrimitive(String key, boolean defaultValue) {
		return config.has(key) ? config.getAsJsonPrimitive(key).getAsBoolean() : defaultValue;
	}

	public synchronized char getPrimitive(String key, char defaultValue) {
		return config.has(key) ? config.getAsJsonPrimitive(key).getAsCharacter() : defaultValue;
	}

	public synchronized String getPrimitive(String key, String defaultValue) {
		return config.has(key) ? config.getAsJsonPrimitive(key).getAsString() : defaultValue;
	}

	// Setters

	public synchronized Settings putPrimitive(String key, int value) {
		config.add(key, new JsonPrimitive(value));
		return this;
	}

	public synchronized Settings putPrimitive(String key, float value) {
		config.add(key, new JsonPrimitive(value));
		return this;
	}

	public synchronized Settings putPrimitive(String key, double value) {
		config.add(key, new JsonPrimitive(value));
		return this;
	}

	public synchronized Settings putPrimitive(String key, long value) {
		config.add(key, new JsonPrimitive(value));
		return this;
	}

	public synchronized Settings putPrimitive(String key, short value) {
		config.add(key, new JsonPrimitive(value));
		return this;
	}

	public synchronized Settings putPrimitive(String key, byte value) {
		config.add(key, new JsonPrimitive(value));
		return this;
	}

	public synchronized Settings putPrimitive(String key, boolean value) {
		config.add(key, new JsonPrimitive(value));
		return this;
	}

	public synchronized Settings putPrimitive(String key, char value) {
		config.add(key, new JsonPrimitive(value));
		return this;
	}

	public synchronized Settings putPrimitive(String key, String value) {
		config.add(key, new JsonPrimitive(value));
		return this;
	}

	public synchronized Settings remove(String key) {
		if (config.has(key)) {
			config.remove(key);
		}
		return this;
	}

	/*
		Thread-safe types
	 */

	// Because Minecraft doesnt use Gson >=2.8.2 which exposes the Gson deepCopy method, we have to use our own
	public static <T> T deepCopy(T object, Class<T> type) {
		try {
			Gson gson = new Gson(); return gson.fromJson(gson.toJson(object, type), type);
		} catch (Exception ignored) { }
		return null;
	}

	// Getters

	public synchronized JsonObject deepCopy() {
		return deepCopy(config, JsonObject.class);
	}

	public synchronized JsonObject getObject(String key) {
		return config.has(key) ? deepCopy(config.getAsJsonObject(key), JsonObject.class) : null;
	}

	public synchronized JsonArray getArray(String key) {
		return config.has(key) ? deepCopy(config.getAsJsonArray(key), JsonArray.class) : null;
	}

	// Setters

	public synchronized Settings putObject(String key, JsonObject object) {
		config.add(key, deepCopy(object, JsonObject.class));
		return this;
	}

	public synchronized Settings putArray(String key, JsonArray object) {
		config.add(key, deepCopy(object, JsonArray.class));
		return this;
	}

}
