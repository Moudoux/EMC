package me.deftware.client.framework.main.preprocessor;

/**
 * Allows maven dependencies to run code prior to Minecraft launching
 *
 * @author Deftware
 */
public abstract class ModPreProcessor implements Runnable {

	public PreProcessorMan preProcessor;

	public abstract String getName();

	public void log(String line) { }

}
