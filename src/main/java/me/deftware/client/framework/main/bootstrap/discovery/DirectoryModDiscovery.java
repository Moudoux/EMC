package me.deftware.client.framework.main.bootstrap.discovery;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.main.bootstrap.Bootstrap;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class DirectoryModDiscovery extends AbstractModDiscovery {

	@Override
	public void discover() {
		Arrays.stream(Objects.requireNonNull(Bootstrap.EMC_ROOT.listFiles())).forEach(file -> {
			File deleteFile = new File(file.getAbsolutePath() + ".delete"),
					updateFile = new File(file.getAbsolutePath() + ".update");
			if (!file.isDirectory() && file.getName().endsWith(".jar")) {
				if (deleteFile.exists()) {
					Bootstrap.logger.info("Deleting {}", file.getName());
					if (!deleteFile.delete() || !file.delete()) {
						Bootstrap.logger.error("Failed to delete {}", file.getName());
					}
				} else {
					if (updateFile.exists()) {
						if (!file.delete() || !updateFile.renameTo(file)) {
							Bootstrap.logger.error("Failed to update {}", file.getName());
						} else {
							Bootstrap.logger.info("Updated {}", file.getName());
						}
					}
					Bootstrap.logger.debug("Discovered {} with DirectoryModDiscovery", file.getName());
					try {
						DirectoryModEntry modEntry = new DirectoryModEntry(file);
						entries.add(modEntry);
					} catch (Exception ex) {
                        Bootstrap.logger.debug(ex);
                        Bootstrap.logger.warn("Failed to load mod {}, is it an EMC mod?", file.getName());
					}
				}
			}
		});
	}

	public static class DirectoryModEntry extends AbstractModEntry {

		private final URLClassLoader classLoader;

		DirectoryModEntry(File file) throws Exception {
			super(file, null);
			JarURLConnection connection = (JarURLConnection) new URL("jar", "", "file:" + getFile().getAbsolutePath() + "!/client.json").openConnection();
			// Create classLoader
			classLoader = URLClassLoader.newInstance(
					new URL[]{new URL("jar", "", "file:" + getFile().getAbsolutePath() + "!/")},
					Bootstrap.class.getClassLoader());
			// Open and read json file
			BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			json = new Gson().fromJson(buffer.lines().collect(Collectors.joining("\n")), JsonObject.class);
			buffer.close();
		}

		@Override
		public void init() {
			// No initialization needs to be done as directory mods are not auto updated
		}

		@Override
		public EMCMod toInstance() throws Exception {
			// Load main class from classLoader
			EMCMod instance = (EMCMod) classLoader.loadClass(getJson().get("main").getAsString()).newInstance();
			// Set instance classLoader
			instance.classLoader = classLoader;
			return instance;
		}

	}

}
