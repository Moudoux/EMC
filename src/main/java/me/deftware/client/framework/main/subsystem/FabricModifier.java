package me.deftware.client.framework.main.subsystem;

import lombok.Getter;
import me.deftware.client.framework.util.path.LocationUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Deftware
 */
@Getter
public class FabricModifier {

	/**
	 * The original fabric loader instance
	 */
	private final FabricLoader original = FabricLoaderImpl.INSTANCE;

	/**
	 * The custom EMC mods directory
	 */
	private final Path modsDir;

	public FabricModifier() throws Exception {
		File emcJar = LocationUtil.getEMC().toFile();
		if (emcJar == null)
			throw new IOException("Unable to find EMC jar");
		modsDir = emcJar.getParentFile().toPath();
	}

	public void run() throws Throwable {
		FabricLoader loader = new FabricLoaderImpl() {
			private final Logger logger = LogManager.getLogger("EMC|Subsystem");

			@Override
			public File getModsDirectory() {
				return modsDir.toFile();
			}

			@Override
			public Path getInternalModsDirectory() {
				return modsDir;
			}

			@Override
			public void load() {
				logger.info("Loading modified Fabric loader by EMC");
				super.load();
			}

		};
		// Replace the fabric instance with our custom one
		ClassModifier<FabricLoaderImpl> modifier = new ClassModifier<>(FabricLoaderImpl.class);
		modifier.setField("INSTANCE", loader);
	}

}
