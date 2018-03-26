package me.deftware.client.framework.main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.apis.marketplace.MarketplaceAPI;
import me.deftware.client.framework.fonts.Fonts;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public enum Bootstrap {
	INSTANCE;

	public static ArrayList<String> commandTriggers = new ArrayList<>();
	public static Logger logger = LogManager.getLogger();
	private static URLClassLoader clientLoader;
	public static ArrayList<JsonObject> modsInfo = new ArrayList<>();
	private static ConcurrentHashMap<String, EMCMod> mods = new ConcurrentHashMap<>();

	public void init() {
		try {
			Bootstrap.logger.info("Loading EMC...");

			Bootstrap.registerCommandTrigger(".");

			// Initialize framework stuff
			Fonts.loadFonts();

			File minecraft = new File(Minecraft.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			File mods = new File(minecraft.getParent() + File.separator + "mods");

			if (!mods.exists()) {
				mods.mkdir();
			}

			for (File fileEntry : mods.listFiles()) {
				if (fileEntry.isDirectory()) {
					continue;
				}
				if (fileEntry.getName().endsWith(".jar")) {
					try {
						if (new File(fileEntry.getAbsolutePath() + ".delete").exists()) {
							Bootstrap.logger.info("Deleting mod " + fileEntry.getName() + "...");
							new File(fileEntry.getAbsolutePath() + ".delete").delete();
							fileEntry.delete();
						} else {
							Bootstrap.loadMod(fileEntry);
						}
					} catch (Exception ex) {
						Bootstrap.logger.warn("Failed to load some EMC mod: " + fileEntry.getName());
						ex.printStackTrace();
					}
				}
			}

			if (new File(minecraft.getParent() + File.separator + "Client_update.jar").exists()) {
				new File(minecraft.getParent() + File.separator + "Client.jar").delete();
				new File(minecraft.getParent() + File.separator + "Client_update.jar")
						.renameTo(new File(minecraft.getParent() + File.separator + "Client.jar"));
			}

			File clientFile = new File(minecraft.getParent() + File.separator + "Client.jar");
			if (clientFile.exists()) {
				try {
					Bootstrap.loadMod(clientFile);
				} catch (Exception ex) {
					Bootstrap.logger.warn("Failed to load main client mod");
					ex.printStackTrace();
				}
			}

			MarketplaceAPI.init((status) -> Bootstrap.mods.forEach((name, mod) -> mod.onMarketplaceAuth(status)));
		} catch (Exception ex) {
			Bootstrap.logger.warn("Failed to load EMC");
			ex.printStackTrace();
		}
	}

	public static void loadMod(File clientJar) throws Exception {
		// Find the client jar

		if (!clientJar.exists()) {
			throw new Exception("Specified mod jar not found");
		}

		// Load client

		JarFile jarFile = new JarFile(clientJar);
		Enumeration e = jarFile.entries();

		URL jarfile = new URL("jar", "", "file:" + clientJar.getAbsolutePath() + "!/");
		Bootstrap.clientLoader = URLClassLoader.newInstance(new URL[]{jarfile});

		// Read client.json

		InputStream in = Bootstrap.clientLoader.getResourceAsStream("client.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder result = new StringBuilder("");

		String line;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		in.close();

		JsonObject jsonObject = new Gson().fromJson(result.toString(), JsonObject.class);
		Bootstrap.modsInfo.add(jsonObject);

		Bootstrap.logger.info("Loading mod: " + jsonObject.get("name").getAsString() + " by "
				+ jsonObject.get("author").getAsString());

		if (jsonObject.get("minversion").getAsDouble() > FrameworkConstants.VERSION) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiUpdateLoader(jsonObject));
			jarFile.close();
			return;
		}

		Bootstrap.mods.put(jsonObject.get("name").getAsString(),
				(EMCMod) Bootstrap.clientLoader.loadClass(jsonObject.get("main").getAsString()).newInstance());

		for (JarEntry je = (JarEntry) e.nextElement(); e.hasMoreElements(); je = (JarEntry) e.nextElement()) {
			if (je.isDirectory() || !je.getName().endsWith(".class")) {
				continue;
			}
			String className = je.getName().replace(".class", "").replace('/', '.');
			Bootstrap.logger.info("Loaded class " + Bootstrap.clientLoader.loadClass(className).getName());
		}

		jarFile.close();

		Bootstrap.mods.get(jsonObject.get("name").getAsString()).init(jsonObject);

		Bootstrap.logger.info("Loaded mod");
	}


	public static void callMethod(String mod, String method, String caller) {
		if (Bootstrap.mods.containsKey(mod)) {
			Bootstrap.mods.get(mod).callMethod(method, caller);
		}
	}

	public static ConcurrentHashMap<String, EMCMod> getClients() {
		return Bootstrap.mods;
	}

	public static void ejectClients() {
		Bootstrap.mods.forEach((key, mod) -> {
			mod.onUnload();
		});
		Bootstrap.mods.clear();
	}

	/*
		Command triggers
	 */

	public static void registerCommandTrigger(String trigger) {
		Bootstrap.logger.info("Registering EMC command trigger: " + trigger);
		if (!Bootstrap.commandTriggers.contains(trigger)) {
			Bootstrap.commandTriggers.add(trigger);
		}
	}

	public static void unregisterCommandTrigger(String trigger) {
		if (Bootstrap.commandTriggers.contains(trigger)) {
			Bootstrap.commandTriggers.remove(trigger);
		}
	}

	public static String isTrigger(String message) {
		for (String trigger : Bootstrap.commandTriggers) {
			if (message.startsWith(trigger)) {
				return trigger;
			}
		}
		return "";
	}

}
