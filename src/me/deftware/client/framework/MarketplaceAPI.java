package me.deftware.client.framework;

import java.net.CookieManager;

import me.deftware.client.framework.Client.EMCClient;
import me.deftware.client.framework.MC_OAuth.OAuth;
import me.deftware.client.framework.Utils.WebUtils;

public class MarketplaceAPI {
	private static final String APIDomain = "https://emc-api.sky-net.me";
	private static CookieManager Cookies;
	private static boolean initialized = false;

	public static void init() {
		if (initialized) {
			return;
		}
		initialized = true;
		login((status) -> System.out
				.println(status ? "EMC API initialized" : "EMC API initialization failed, could not get oAuth token"));
	}

	private static void login(LoginCallback callback) {
		OAuth.oAuth((boolean success, String token, String time) -> {
			if (success) {
				String response = WebUtils.get(getLoginURL(token), Cookies);

				if (response.equals("Successfully logged in")) {
					Cookies = WebUtils.LastCookies;
					callback.cb(true);
				} else
					callback.cb(false);
			}
		});
	}

	interface LoginCallback {
		void cb(boolean success);
	}

	public static String checkLicensed(EMCClient.EMCClientInfo clientInfo) {
		return WebUtils.get(getCheckLicensedURL(clientInfo.getClientName(), false), Cookies);
	}

	public static String checkLicensedSecure(EMCClient.EMCClientInfo clientInfo) {
		return WebUtils.get(getCheckLicensedURL(clientInfo.getClientName(), true), Cookies);
	}

	public static String getKey(EMCClient.EMCClientInfo clientInfo) {
		return WebUtils.get(getKeyURL(clientInfo.getClientName()), Cookies);
	}

	public static String downloadMod(EMCClient.EMCClientInfo clientInfo) {
		return WebUtils.get(getDownloadURL(clientInfo.getClientName()), Cookies);
	}

	private static String getKeyURL(String productName) {
		return APIDomain + "/user/products/" + productName + "/get-key";
	}

	private static String getCheckLicensedURL(String productName, boolean secure) {
		if (secure)
			return APIDomain + "/user/products/" + productName + "/is-licensed-secure";
		else
			return APIDomain + "/user/products/" + productName + "/is-licensed";
	}

	private static String getDownloadURL(String productName) {
		return APIDomain + "/user/products/" + productName + "/download";
	}

	private static String getLoginURL(String code) {
		return APIDomain + "/user/login/" + code;
	}
}
