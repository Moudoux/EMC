package me.deftware.client.framework.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import me.deftware.client.framework.FrameworkConstants;


public class WebUtils
{
    public static CookieManager LastCookies = new CookieManager();
    private static final String COOKIES_HEADER = "Set-Cookie";

    public static String get(String requestUrl)
    {
        return get(requestUrl, null);
    }

    public static String get(String requestUrl, CookieManager cookies)
    {
        try
        {
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            if (cookies != null)
                applyCookies(connection);

            connection.setConnectTimeout(8 * 1000);
            connection.setRequestProperty("User-Agent", FrameworkConstants.FRAMEWORK_NAME);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String text;
            StringBuilder result = new StringBuilder();
            while ((text = in.readLine()) != null)
                result.append(text);

            in.close();

            storeCookies(connection);
            return result.toString();
        } catch (Exception e)
        {
            return "";
        }
    }

    public static String sendPostRequest(String requestUrl, String payload)
    {
        return sendPostRequest(requestUrl, payload, null);
    }

    public static String sendPostRequest(String requestUrl, String payload, CookieManager cookies)
    {
        try
        {
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            if (cookies != null)
                applyCookies(connection);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),
                    "UTF-8");
            writer.write(payload);
            writer.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                jsonString.append(line);

            br.close();
            connection.disconnect();

            storeCookies(connection);
            return jsonString.toString();
        } catch (Exception e)
        {
            return "";
        }
    }

    private static void storeCookies(URLConnection urlConnection)
    {
        Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

        if (cookiesHeader == null)
            return;

        LastCookies = new CookieManager();
        for (String cookie : cookiesHeader)
            LastCookies.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
    }

    private static void applyCookies(URLConnection urlConnection)
    {
        if (LastCookies.getCookieStore().getCookies().size() <= 0)
            return;

        StringBuilder cookieHeader = new StringBuilder();
        for (HttpCookie cookie : LastCookies.getCookieStore().getCookies())
            cookieHeader.append(cookie).append(";");
        cookieHeader.delete(cookieHeader.length() - 1, 1);

        urlConnection.setRequestProperty("Cookie", cookieHeader.toString());
    }
}
