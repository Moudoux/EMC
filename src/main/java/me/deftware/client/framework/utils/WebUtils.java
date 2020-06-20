package me.deftware.client.framework.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Various web utils for reading data of websites
 */
public class WebUtils {

	private static CookieManager lastCookies = new CookieManager();
	private static final String COOKIES_HEADER = "Set-Cookie";

	public static CookieManager getLastCookies() {
		return WebUtils.lastCookies;
	}

	public static String get(String requestUrl) throws Exception {
		return WebUtils.get(requestUrl, null);
	}

	public static String urlEncode(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(),
				url.getQuery(), url.getRef()).toASCIIString();
	}

	public static String get(String requestUrl, CookieManager cookies) throws Exception {
		URL url = new URL(WebUtils.urlEncode(requestUrl));
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		if (cookies != null) {
			WebUtils.applyCookies(connection);
		}

		connection.setConnectTimeout(8 * 1000);
		connection.setRequestProperty("User-Agent", "EMC");
		connection.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String text;
		StringBuilder result = new StringBuilder();
		while ((text = in.readLine()) != null) {
			result.append(text);
		}

		in.close();

		WebUtils.storeCookies(connection);
		return result.toString();
	}

	public static String post(String requestUrl, String payload) throws Exception {
		return WebUtils.post(requestUrl, payload, null);
	}

	public static String post(String requestUrl, String payload, CookieManager cookies) throws Exception {
		URL url = new URL(WebUtils.urlEncode(requestUrl));
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		if (cookies != null) {
			WebUtils.applyCookies(connection);
		}

		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
		writer.write(payload);
		writer.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder jsonString = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			jsonString.append(line);
		}

		br.close();
		connection.disconnect();

		WebUtils.storeCookies(connection);
		return jsonString.toString();
	}

	private static void storeCookies(URLConnection urlConnection) {
		Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
		List<String> cookiesHeader = headerFields.get(WebUtils.COOKIES_HEADER);

		if (cookiesHeader == null) {
			return;
		}

		WebUtils.lastCookies = new CookieManager();
		for (String cookie : cookiesHeader) {
			WebUtils.lastCookies.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
		}
	}

	private static void applyCookies(URLConnection urlConnection) {
		if (WebUtils.lastCookies.getCookieStore().getCookies().size() < 1) {
			return;
		}

		StringBuilder cookieHeader = new StringBuilder();
		for (HttpCookie cookie : WebUtils.lastCookies.getCookieStore().getCookies()) {
			cookieHeader.append(cookie).append(";");
		}
		cookieHeader.deleteCharAt(cookieHeader.length() - 1);

		urlConnection.setRequestProperty("Cookie", cookieHeader.toString());
	}

	public static String getMavenUrl(String name, String url) {
		String[] data = name.split(":");
		String type = data.length > 3 ? data[3] : "";
		return String.format("%s%s/%s/%s/%s-%s%s.jar", url, data[0].replaceAll("\\.", "/"), data[1], data[2], data[1], data[2], type);
	}

}
