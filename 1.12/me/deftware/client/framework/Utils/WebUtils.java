package me.deftware.client.framework.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import me.deftware.client.framework.FrameworkConstants;

public class WebUtils {

	public static String get(String url) throws Exception {
		try {
			URL url1 = new URL(url);
			Object connection = (url.startsWith("https://") ? (HttpsURLConnection) url1.openConnection()
					: (HttpURLConnection) url1.openConnection());
			((URLConnection) connection).setConnectTimeout(8 * 1000);
			((URLConnection) connection).setRequestProperty("User-Agent", FrameworkConstants.FRAMEWORK_NAME);

			((HttpURLConnection) connection).setRequestMethod("GET");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(((URLConnection) connection).getInputStream()));
			String text;
			String result = "";
			while ((text = in.readLine()) != null) {
				result = result + text;
			}
			in.close();
			return result;
		} catch (Exception e) {
			return "null";
		}
	}

	public static String sendPostRequest(String requestUrl, String payload) {
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer jsonString = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
			return jsonString.toString();
		} catch (Exception e) {
			;
		}
		return "";
	}

}
