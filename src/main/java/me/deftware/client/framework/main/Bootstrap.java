package me.deftware.client.framework.main;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.apis.marketplace.MarketplaceAPI;
import me.deftware.client.framework.fonts.Fonts;
import me.deftware.client.framework.utils.OSUtils;
import net.minecraft.client.Minecraft;

import javax.print.attribute.Attribute;

public class Bootstrap {

	public static ArrayList<String> commandTriggers = new ArrayList<>();
	public static Logger logger = LogManager.getLogger();
	private static URLClassLoader modClassLoader;
	public static ArrayList<JsonObject> modsInfo = new ArrayList<>();
	private static ConcurrentHashMap<String, EMCMod> mods = new ConcurrentHashMap<>();

	public static void init() {
		try {
			Bootstrap.logger.info("Loading EMC...");

			try {
				Enumeration<URL> resources = Bootstrap.class.getClassLoader()
						.getResources("META-INF/MANIFEST.MF");
				while (resources.hasMoreElements()) {
					try {
						Manifest manifest = new Manifest(resources.nextElement().openStream());
						for (Object o : manifest.getMainAttributes().keySet()) {
							if (o.toString().equals("EMC-Version")) {
								String EMC_VERSION = String.valueOf(manifest.getMainAttributes().getValue("EMC-Version"));
								FrameworkConstants.VERSION = Double.valueOf(EMC_VERSION.substring(0, EMC_VERSION.length() - EMC_VERSION.split("\\.")[2].length() - 1));
								FrameworkConstants.PATCH = Integer.valueOf(EMC_VERSION.split("\\.")[2]);
								Bootstrap.logger.info("EMC version: " + FrameworkConstants.VERSION + " patch " + FrameworkConstants.PATCH);
								break;
							}
						}
					} catch (IOException E) {
						E.printStackTrace();
					}
				}
			} catch (IOException E) {
				E.printStackTrace();
			}

			Bootstrap.registerCommandTrigger(".");
			Fonts.loadFonts();

			File emc_root = new File(OSUtils.getMCDir() + "libraries" + File.separator + "EMC" + File.separator);
			if (!emc_root.exists()) {
				emc_root.mkdir();
			}

			for (File fileEntry : emc_root.listFiles()) {
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
							File udpateJar = new File(emc_root.getAbsolutePath() + File.separator
									+ fileEntry.getName().substring(0, fileEntry.getName().length() - ".jar".length())
									+ "_update.jar");
							if (udpateJar.exists()) {
								fileEntry.delete();
								udpateJar.renameTo(fileEntry);
							}
							Bootstrap.loadMod(fileEntry);
						}
					} catch (Exception ex) {
						Bootstrap.logger.warn("Failed to load EMC mod: " + fileEntry.getName());
						ex.printStackTrace();
					}
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
		Enumeration<?> e = jarFile.entries();

		URL jarfile = new URL("jar", "", "file:" + clientJar.getAbsolutePath() + "!/");
		Bootstrap.modClassLoader = URLClassLoader.newInstance(new URL[] { jarfile }, Bootstrap.class.getClassLoader());

		// Read client.json

		InputStream in = Bootstrap.modClassLoader.getResourceAsStream("client.json");
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

		Class<?> c = Bootstrap.modClassLoader.loadClass(jsonObject.get("main").getAsString());
		Bootstrap.mods.put(jsonObject.get("name").getAsString(), (EMCMod) c.newInstance());

		for (JarEntry je = (JarEntry) e.nextElement(); e.hasMoreElements(); je = (JarEntry) e.nextElement()) {
			if (je.isDirectory() || !je.getName().endsWith(".class")) {
				continue;
			}
			String className = je.getName().replace(".class", "").replace('/', '.');
			Bootstrap.logger.info("Loaded class " + Bootstrap.modClassLoader.loadClass(className).getName());
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
		Bootstrap.mods.forEach((key, mod) -> mod.onUnload());
		Bootstrap.mods.clear();
	}

	/*
	 * Command triggers
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
