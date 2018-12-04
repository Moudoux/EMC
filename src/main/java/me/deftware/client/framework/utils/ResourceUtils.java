package me.deftware.client.framework.utils;

import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.wrappers.IMinecraft;
import net.minecraft.realms.RealmsSharedConstants;

import java.io.*;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResourceUtils {

    public static InputStream getStreamFromModResources(String modName, String resourcePath) {
        String jarName = "";
        for (int mods = 0; mods < Bootstrap.modsInfo.size(); mods++) {
            if (Bootstrap.modsInfo.get(mods).get("name").getAsString().equals(modName)) {
                jarName = Bootstrap.modsInfo.get(mods).get(Bootstrap.JSON_JARNAME_NOTE).getAsString();
                System.out.println("Found a jar with a name " + jarName);
                break;
            }
        }
        File modJar = new File(OSUtils.getMCDir() + "libraries" + File.separator + "EMC" + File.separator
                + RealmsSharedConstants.VERSION_STRING + File.separator + jarName);

        System.out.println("Reading JAR file: " + modJar.getAbsolutePath());

        InputStream in;
        try {
            ZipFile zipFile = new ZipFile(modJar);
            ZipEntry entry = zipFile.getEntry(resourcePath);

            in = zipFile.getInputStream(entry);
        } catch (IOException e) {
            Bootstrap.logger.warn("Requested resource does not exist", e);
            return null;
        }
        return in;
    }

    public static InputStream getStreamFromMinecraftResources(String resourcePath) {
        InputStream resource;
        try {
            System.out.println("Getting resource from: " + IMinecraft.getRunningLocation() + File.separator + resourcePath);
            resource = new FileInputStream(IMinecraft.getRunningLocation() + File.separator + resourcePath);
        } catch (URISyntaxException | FileNotFoundException e) {
            Bootstrap.logger.warn("Requested resource does not exist", e);
            return null;
        }
        return resource;
    }

    public static InputStream getStreamFromUserspace(String resourcePath) {
        InputStream resource;
        try {
            if (OSUtils.isWindows() || OSUtils.isLinux() || OSUtils.isMac()) {
                resource = new FileInputStream(System.getProperty("user.home") + File.separator + resourcePath);
            } else { //Probably unix-like
                resource = new FileInputStream("/home/" + System.getProperty("user.name") + File.separator + resourcePath);
            }
            System.out.println("Getting resource from: " + System.getProperty("user.home") + File.separator + resourcePath);

        } catch (FileNotFoundException e) {
            Bootstrap.logger.warn("Requested resource does not exist", e);
            return null;
        }
        return resource;
    }
}

