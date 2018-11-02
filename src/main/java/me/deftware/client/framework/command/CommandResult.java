package me.deftware.client.framework.command;

/**
 * Result of a command dispatch
 */
public class CommandResult {

	private boolean success;
	private int output;
	private String error;

	public CommandResult(boolean success, int output, String error) {
		this.success = success;
		this.output = output;
		this.error = error;
	}

	/**
	 * Checks if command successfully executed
	 * @return {@link Boolean}
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * Returns integer returned by the command executed
	 * @return {@link Integer}
	 */
	public int getOutput() {
		return output;
	}

	/**
	 * Returns the error if a command failed. This is also used to describe why the command failed
	 * e.g if theres an argument missing
	 *
	 * @return {@link String}
	 */
	public String getError() {
		return error;
	}

}
