package me.deftware.client.framework.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.deftware.client.framework.utils.WebUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Downloads and installs mods from maven repos
 */
public class ModInstaller {

    public static void load(JsonArray emcMods) {
        Bootstrap.logger.info("Checking additional EMC mods...");
        emcMods.forEach(jsonElement -> {
            try {
                JsonObject element = jsonElement.getAsJsonObject();
                String modName = element.get("name").getAsString();
                String[] maven = element.get("maven").getAsString().split(":");
                String url = element.get("url").getAsString() + maven[0].replace(".", "/") + "/" + maven[1] + "/" + maven[2] + "/" + maven[1] + "-" + maven[2] + ".jar", sha1 = url + ".sha1";
                File modFile = new File(Bootstrap.emc_root.getAbsolutePath() + File.separator + element.get("name").getAsString() + ".jar");
                String remoteSHA1 = WebUtils.get(sha1);
                if (modFile.exists()) {
                    Bootstrap.logger.info("Verifying SHA-1 of " + modName);
                    String fileSHA1 = computeFileSHA1(modFile);
                    if (!fileSHA1.trim().equalsIgnoreCase(remoteSHA1.trim())) {
                        Bootstrap.logger.warn("SHA-1 not matching, reinstalling additional EMC mod " + modName);
                        if (!modFile.delete()) {
                            Bootstrap.logger.warn("Could not delete old additional EMC mod " + modFile.getAbsolutePath());
                        }
                        installMod(modFile, url);
                    }
                } else {
                    installMod(modFile, url);
                }
            } catch (Exception ex) {
                Bootstrap.logger.error("Failed to install additional EMC mod");
                ex.printStackTrace();
            }
        });
    }

    public static void installMod(File modFile, String url) throws Exception {
        Bootstrap.logger.info("Installing additional EMC mod " + modFile.getName());
        downloadFile(url, modFile);
    }

    private static void downloadFile(String uri, File file) {
        try {
            URL url = new URL(uri);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            connection.setRequestMethod("GET");
            FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
            InputStream in = connection.getInputStream();
            int read;
            byte[] buffer = new byte[4096];
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static String computeFileSHA1(File file) throws IOException {
        String sha1 = null;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            throw new IOException("Impossible to get SHA-1 digester", e1);
        }
        try (InputStream input = new FileInputStream(file);
             DigestInputStream digestStream = new DigestInputStream(input, digest)) {
            while (digestStream.read() != -1) {
                // read file stream without buffer
            }
            MessageDigest msgDigest = digestStream.getMessageDigest();
            sha1 = new HexBinaryAdapter().marshal(msgDigest.digest());
        }
        return sha1;
    }


}
