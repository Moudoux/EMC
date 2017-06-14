package me.deftware.client.framework.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.Client.EMCClient;
import me.deftware.client.framework.FontRender.Fonts;
import net.minecraft.client.Minecraft;

public class FrameworkLoader {

	public static Logger logger = Logger.getLogger("Minecraft");

	private static URLClassLoader clientLoader;

	/**
	 * Info about the loaded client
	 */
	public static JsonObject clientInfo = null;

	/**
	 * Our client instance
	 */
	private static EMCClient client;

	/**
	 * Called from the Minecraft initialization method
	 */
	public static void init() {
		try {
			logger.info("Loading client framework...");

			// Initialize framework stuff
			Fonts.loadFonts();

			// Find the client jar
			File minecraft = new File(
					Minecraft.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			File clientJar = new File(minecraft.getParent() + File.separator + "Client.jar");

			if (!clientJar.exists()) {
				throw new Exception("Client jar not found");
			}

			// Load client

			URL jarfile = new URL("jar", "", "file:" + clientJar.getAbsolutePath() + "!/");
			clientLoader = URLClassLoader.newInstance(new URL[] { jarfile });

			// Read client.json

			InputStream in = clientLoader.getResourceAsStream("client.json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder result = new StringBuilder("");

			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			in.close();

			JsonObject jsonObject = new Gson().fromJson(result.toString(), JsonObject.class);
			clientInfo = jsonObject;

			logger.info("Loading client: " + jsonObject.get("name").getAsString() + " by "
					+ jsonObject.get("author").getAsString());

			if (jsonObject.get("minversion").getAsInt() > FrameworkConstants.VERSION) {
				Minecraft.getMinecraft().displayGuiScreen(new GuiUpdateLoader(jsonObject));
				return;
			}

			client = (EMCClient) clientLoader.loadClass(jsonObject.get("main").getAsString()).newInstance();
			client.init();

			logger.info("Loaded client jar");

		} catch (Exception ex) {
			logger.warning("Failed to load client framework");
			ex.printStackTrace();
		}
	}

	public static EMCClient getClient() {
		return client;
	}

	/**
	 * Unloads the client
	 */
	public static void ejectClient() {
		client = null;
		try {
			clientLoader.close();
			clientLoader = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reloads the client
	 */
	public static void reload() {
		ejectClient();
		init();
	}

}
