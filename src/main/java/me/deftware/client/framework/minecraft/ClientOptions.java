package me.deftware.client.framework.minecraft;

/**
 * @author Deftware
 */
public interface ClientOptions {

	/**
	 * @return The block view distance
	 */
	default int getBlockViewDistance() {
		return this.getViewDistance() * 16;
	}

	/**
	 * @return The view distance, in chunks
	 */
	int getViewDistance();

	/**
	 * @return If the game is in fullscreen
	 */
	boolean isFullScreen();

}
