package me.deftware.client.framework.MC_OAuth;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StaticOAuth {

	private static String token = "";

	static {
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				token = "";
			}
		}, 0, 2, TimeUnit.MINUTES);
	}

	/**
	 * Will always return a token
	 * 
	 * @param callback
	 */
	public static void getToken(OAuthCodeHandler callback) {
		if (token.equals("")) {
			OAuth.oAuth((success, code, time) -> {
				token = code;
				callback.callback(token);
			});
		} else {
			callback.callback(token);
		}
	}

	@FunctionalInterface
	public static interface OAuthCodeHandler {

		public void callback(String code);

	}

}
