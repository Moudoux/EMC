package me.deftware.client.framework.lang;

/**
 * TODO
 *
 * @author Deftware
 */
public interface ILanguage {

	String getFile();

	String getIsoCode();

	boolean translateColorCodes();

	boolean isAvailable();

	boolean hasKey(String key);

	String get(String key, Object... args);

}
