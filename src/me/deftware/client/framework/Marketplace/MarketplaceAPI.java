package me.deftware.client.framework.Marketplace;

import java.net.CookieManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.deftware.client.framework.Client.EMCClient;
import me.deftware.client.framework.MC_OAuth.OAuth;
import me.deftware.client.framework.Utils.WebUtils;

public class MarketplaceAPI {
	private static final String APIDomain = "https://emc-api.sky-net.me";
	private static CookieManager cookies;
	private static boolean initialized = false;

	public static void init(LoginCallback callback) {
		if (initialized)
			return;
		login((status) -> {
			System.out.println(
					status ? "EMC API initialized" : "EMC API initialization failed, could not get oAuth token");
			initialized = true;
			callback.cb(status);
		});
	}

	private static void login(LoginCallback callback) {
		OAuth.oAuth((boolean success, String token, String time) -> {
			if (success) {
				MarketplaceResponse response = parseResponse(getSafe(getLoginURL(token)));

				if (response.success) {
					cookies = WebUtils.getLastCookies();
					callback.cb(true);
				} else
					callback.cb(false);
			}
		});
	}

	public static interface LoginCallback {
		void cb(boolean success);
	}

	/**
	 * Checks if the product is licensed
	 *
	 * @param clientInfo
	 *            the mod's info
	 * @return {@link MarketplaceResponse} with licensed status in
	 *         {@link MarketplaceResponse#success}
	 */
	public static MarketplaceResponse checkLicensed(EMCClient.EMCClientInfo clientInfo) {
		return parseResponse(getSafe(getCheckLicensedURL(clientInfo.getClientName())));
	}

	/**
	 * Checks if the product is licensed, securely The response will be the supplied
	 * {@code secret}, encrypted with the mod's {@code private key}
	 * <p>
	 * To protect against response forging and emulation, verify that the decrypted
	 * response matches the supplied secret
	 *
	 * @param clientInfo
	 *            the mod's info
	 * @param secret
	 *            a unique string (e.g. timestamp) that will be returned in the
	 *            encrypted message
	 * @return {@link MarketplaceResponse} with licensed status in
	 *         {@link MarketplaceResponse#success} and the encrypted {@code secret}
	 *         in {@link MarketplaceResponse#data}
	 */
	public static MarketplaceResponse checkLicensedSecure(EMCClient.EMCClientInfo clientInfo, String secret) {
		return parseResponse(getSafe(getCheckLicensedURL(clientInfo.getClientName(), secret)));
	}

	/**
	 * Retrieves a product key if licensed
	 *
	 * @param clientInfo
	 *            the mod's info
	 * @return {@link MarketplaceResponse} with the key in
	 *         {@link MarketplaceResponse#data}
	 */
	public static MarketplaceResponse getKey(EMCClient.EMCClientInfo clientInfo) {
		return parseResponse(getSafe(getKeyURL(clientInfo.getClientName())));
	}

	/**
	 * Retrieves a mod if licensed
	 *
	 * @param clientInfo
	 *            the mod's info
	 * @return {@link MarketplaceResponse} with the mod's bytes base64-encoded in
	 *         {@link MarketplaceResponse#data}
	 */
	public static MarketplaceResponse downloadMod(EMCClient.EMCClientInfo clientInfo) {
		return parseResponse(getSafe(getDownloadURL(clientInfo.getClientName())));
	}

    /**
     * Retrieves a list with the names of all mods licensed to the user
     *
     * @return {@code String array} of the mod names
     */
    private static String[] getLicensedModNames() {
        return new Gson().fromJson(getSafe(getUserLicensedProductNamesURL()), String[].class);
    }

	private static String getSafe(String url) {
		try {
			return WebUtils.get(url, cookies);
		} catch (Exception e) {
			System.out.println("[EMC] Exception while doing GET request");
			e.printStackTrace();
			return "";
		}
	}

	private static String getKeyURL(String productName) {
		return APIDomain + "/user/products/" + productName + "/get-key";
	}

	private static String getCheckLicensedURL(String productName) {
		return APIDomain + "/user/products/" + productName + "/is-licensed";
	}

	private static String getCheckLicensedURL(String productName, String secret) {
		return APIDomain + "/user/products/" + productName + "/is-licensed-secure/" + secret;
	}

	private static String getDownloadURL(String productName) {
		return APIDomain + "/user/products/" + productName + "/download";
	}

	private static String getUserLicensedProductNamesURL() {
        return APIDomain + "/user/licensed-product-names";
    }

	private static String getLoginURL(String code) {
		return APIDomain + "/user/login/" + code;
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