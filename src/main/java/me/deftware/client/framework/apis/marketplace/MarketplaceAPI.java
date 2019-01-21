package me.deftware.client.framework.apis.marketplace;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.deftware.client.framework.apis.oauth.OAuth;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.utils.WebUtils;

import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

/**
 * API used to talk with the EMC marketplace API
 */
public class MarketplaceAPI {

	private static final String APIDomain = "https://emc-api.aristois.net";
	private static CookieManager cookies;
	private static boolean initialized = false;

	/**
	 * Initializes Marketplace API or does nothing if already initialized
	 * Prints out status to the System.out
	 */
	public static void init(LoginCallback callback) {
		if (MarketplaceAPI.initialized) {
			return;
		}
		MarketplaceAPI.login((status) -> {
			System.out.println(
					status ? "EMC API initialized" : "EMC API initialization failed, could not get oAuth token");
			MarketplaceAPI.initialized = true;
			callback.cb(status);
		});
	}

	/**
	 * Handles login response
	 */
	private static void login(LoginCallback callback) {
		OAuth.oAuth((boolean success, String token, String time) -> {
			if (success) {
				MarketplaceResponse response = MarketplaceAPI.parseResponse(MarketplaceAPI.getSafe(MarketplaceAPI.getLoginURL(token)));

				if (response.success) {
					MarketplaceAPI.cookies = WebUtils.getLastCookies();
					callback.cb(true);
				} else {
					callback.cb(false);
				}
			} else {
				callback.cb(false);
			}
		});
	}

	public interface LoginCallback {
		void cb(boolean success);
	}

	/**
	 * Checks if the product is licensed
	 *
	 * @param modName the mod's name
	 * @return {@link MarketplaceResponse} with licensed status in
	 * {@link MarketplaceResponse#success}
	 */
	public static MarketplaceResponse checkLicensed(String modName) {
		return MarketplaceAPI.parseResponse(MarketplaceAPI.getSafe(MarketplaceAPI.getCheckLicensedURL(modName)));
	}

	/**
	 * Checks if the product is licensed, securely The response will be the supplied
	 * {@code secret}, encrypted with the mod's {@code private key}
	 * <p>
	 * To protect against response forging and emulation, verify that the decrypted
	 * response matches the supplied secret
	 *
	 * @param modName the mod's name
	 * @param secret     a unique string (e.g. timestamp) that will be returned in the
	 *                   encrypted message
	 * @return {@link MarketplaceResponse} with licensed status in
	 * {@link MarketplaceResponse#success} and the encrypted {@code secret}
	 * in {@link MarketplaceResponse#data}
	 */
	public static MarketplaceResponse checkLicensedSecure(String modName, String secret) {
		return MarketplaceAPI.parseResponse(MarketplaceAPI.getSafe(MarketplaceAPI.getCheckLicensedURL(modName, secret)));
	}

	/**
	 * Retrieves a product key if licensed
	 *
	 * @param modName the mod's name
	 * @return {@link MarketplaceResponse} with the key in
	 * {@link MarketplaceResponse#data}
	 */
	public static MarketplaceResponse getKey(String modName) {
		return MarketplaceAPI.parseResponse(MarketplaceAPI.getSafe(MarketplaceAPI.getKeyURL(modName)));
	}

	/**
	 * Retrieves a mod if licensed
	 *
	 * @param name the mod name you want to download
	 * @return {@link MarketplaceResponse} with the mod's bytes base64-encoded in
	 * {@link MarketplaceResponse#data}
	 */
	public static MarketplaceResponse downloadMod(String name) {
		return MarketplaceAPI.parseResponse(MarketplaceAPI.getSafe(MarketplaceAPI.getDownloadURL(name)));
	}

	/**
	 * Retrieves a list with the names of all mods licensed to the user
	 *
	 * @return {@code String array} of the mod names
	 */
	public static List<String> getLicensedModNames() {
		JsonObject json = new Gson().fromJson(MarketplaceAPI.getSafe(MarketplaceAPI.getUserLicensedProductNamesURL()), JsonObject.class);
		JsonArray arr = json.get("data").getAsJsonArray();
		List<String> list = new ArrayList<>();
		arr.forEach((jsonElement) -> list.add(jsonElement.getAsString()));
		return list;
	}

	private static String getSafe(String url) {
		try {
			return WebUtils.get(url, MarketplaceAPI.cookies);
		} catch (Exception e) {
			System.out.println("[EMC] Exception while doing GET request");
			e.printStackTrace();
			return "";
		}
	}

	private static String getKeyURL(String productName) {
		return MarketplaceAPI.APIDomain + "/user/products/" + productName + "/get-key";
	}

	private static String getCheckLicensedURL(String productName) {
		return MarketplaceAPI.APIDomain + "/user/products/" + productName + "/is-licensed";
	}

	private static String getCheckLicensedURL(String productName, String secret) {
		return MarketplaceAPI.APIDomain + "/user/products/" + productName + "/is-licensed-secure/" + secret;
	}

	private static String getDownloadURL(String productName) {
		return MarketplaceAPI.APIDomain + "/user/products/" + productName + "/download";
	}

	private static String getUserLicensedProductNamesURL() {
		return MarketplaceAPI.APIDomain + "/user/licensed-product-names";
	}

	private static String getLoginURL(String code) {
		return MarketplaceAPI.APIDomain + "/user/login/" + code;
	}

	private static MarketplaceResponse parseResponse(String response) {
		MarketplaceResponse mResponse = new MarketplaceResponse();

		try {
			JsonObject json = new Gson().fromJson(response, JsonObject.class);
			mResponse.success = json.get("success").getAsBoolean();
			mResponse.data = json.get("data").getAsString();
		} catch (Exception e) {
			mResponse.success = false;
			mResponse.data = e.getMessage();
		}

		return mResponse;
	}
}
