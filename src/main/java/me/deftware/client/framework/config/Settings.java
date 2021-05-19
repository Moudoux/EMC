package me.deftware.client.framework.config;

import com.google.gson.*;
import me.deftware.client.framework.minecraft.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Simple Json based config for EMC mods
 *
 * @author Deftware
 */
public class Settings {

	public static final Path configDir = Paths.get(Minecraft.getRunDir().getAbsolutePath(), "libraries", "EMC", Minecraft.getMinecraftVersion(), "configs");
	public static final double revision = 4.0;

	private final Queue<Runnable> shutdownQueue = new ConcurrentLinkedQueue<>();
	private JsonObject config = empty();

	private final Logger logger;
	private final File configFile;

	public Settings(String name) {
		this(name, "_config");
	}

	public Settings(String name, String suffix) {
		this.logger = LogManager.getLogger(name + "/" + this.getClass().getSimpleName());
		this.configFile = configDir.resolve(name + suffix + ".json").toFile();
		this.load();
		// Flush
		this.save();
	}

	private void load() {
		try {
			if (this.configFile.exists()) {
				this.logger.debug("Loading {}", this.configFile.getAbsolutePath());
				String contents = getConfigFileContents().trim();
				if (contents.isEmpty())
					throw new Exception("Empty config file");
				this.config = new Gson().fromJson(contents, JsonObject.class);
			}
		} catch (Exception ex) {
			this.logger.error("Failed to read mod config, resetting...", ex);
			this.config = empty();
		}
	}

	private JsonObject empty() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("version", revision);
		return jsonObject;
	}

	public JsonObject getConfig() {
		return config;
	}

	public void setupShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			this.logger.info("Saving {}", configFile.getName());
			for (Runnable consumer : shutdownQueue) {
				consumer.run();
			}
			save();
		}));
	}

	public File getConfigFile() {
		return configFile;
	}

	public synchronized void setConfig(JsonObject json) {
		this.config = json;
	}

	public Queue<Runnable> getShutdownQueue() {
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
			this.logger.error("Failed to save config", ex);
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
