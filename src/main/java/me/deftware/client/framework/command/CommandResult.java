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
	 * @return if the command successfully executed
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @return integer returned by the command executed
	 */
	public int getOutput() {
		return output;
	}

	/**
	 * @return the error if a command failed. This is also used to describe why the command failed
	 * e.g if theres an argument missing
	 */
	public String getError() {
		return error;
	}

}
