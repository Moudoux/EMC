package me.deftware.client.framework.main.preprocessor;

import me.deftware.client.framework.main.Main;

/**
 * Allows maven dependencies to run code prior to Minecraft launching
 *
 * @author Deftware
 */
public abstract class ModPreProcessor implements Runnable {

	public PreProcessorMan preProcessor;

	public abstract String getName();

	public void log(String line) {
		Main.logging.add("[Pre-processor] [" + this.getClass().getName() + "] " + line);
	}

}
