package me.deftware.client.framework.main.preprocessor;

import com.google.gson.JsonObject;
import me.deftware.client.framework.main.Main;
import me.deftware.client.framework.main.bootstrap.discovery.ClasspathModDiscovery;
import me.deftware.client.framework.util.path.OSUtils;

import java.io.File;
import java.util.HashMap;

/**
 * @author Deftware
 */
public class PreProcessorMan implements Runnable {

	private final File emcJar;
	private final ClasspathModDiscovery classpathModDiscovery;
	private final HashMap<String, ModPreProcessor> preProcessorHashMap = new HashMap<>();

	public PreProcessorMan(File emcJar) {
		this.emcJar = emcJar;
		this.classpathModDiscovery = new ClasspathModDiscovery();
		for (JsonObject entry : this.classpathModDiscovery.getClasspathMods()) {
			if (entry.has("preprocessor")) {
				try {
					ModPreProcessor instance = (ModPreProcessor) Main.class.getClassLoader().loadClass(entry.get("preprocessor").getAsString()).newInstance();
					instance.preProcessor = this;
					this.preProcessorHashMap.put(entry.get("id").getAsString(), instance);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		for (ModPreProcessor preProcessor : preProcessorHashMap.values()) {
			preProcessor.run();
		}
	}

	/*
		Helper methods
	 */

	public File getEmcJar() {
		return emcJar;
	}

	public File getEMCModsDir() {
		// TODO: Remove OSUtils.getDefaultMinecraftPath()
		return new File(OSUtils.getDefaultMinecraftPath() + "libraries" + File.separator + "EMC" + File.separator + getMinecraftVersion() + File.separator);
	}

	public String getMinecraftVersion() {
		return emcJar.getName().split("-")[emcJar.getName().split("-").length - 1].replace(".jar", "");
	}

}
