package me.deftware.client.framework.utils;

import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.wrappers.IMinecraft;

import java.io.*;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResourceUtils {

    /*
     * Returns a byte stream of a target resource
     * @param modName Native name of the mod the resource corresponds to (name from client.json
     * @param resourcePath Path to the resource, no slash at the begining is required
     *
     * @return InputStream
     */
    public static InputStream getStreamFromModResources(String modName, String resourcePath) {
        String jarName = "";
        for (int mods = 0; mods < Bootstrap.modsInfo.size(); mods++) {
            if (Bootstrap.modsInfo.get(mods).get("name").getAsString().equals(modName)) {
                jarName = Bootstrap.modsInfo.get(mods).get(Bootstrap.JSON_JARNAME_NOTE).getAsString();
                Bootstrap.logger.debug("Found a jar with a name " + jarName);
                break;
            }
        }
        File modJar = new File(OSUtils.getMCDir(true) + "libraries" + File.separator + "EMC" + File.separator
                + IMinecraft.getMinecraftVersion() + File.separator + jarName);

        Bootstrap.logger.debug("Reading JAR file: " + modJar.getAbsolutePath());

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

    /*
     * Returns a byte stream of a target resource from .minecraft folder
     * @param resourcePath Path to the resource, no slash at the begining is required
     *
     * @return InputStream
     */
    public static InputStream getStreamFromMinecraftResources(String resourcePath) {
        InputStream resource;
        try {
            Bootstrap.logger.debug("Getting resource from: " + IMinecraft.getRunningLocation() + File.separator + resourcePath);
            resource = new FileInputStream(IMinecraft.getRunningLocation() + File.separator + resourcePath);
        } catch (URISyntaxException | FileNotFoundException e) {
            Bootstrap.logger.warn("Requested resource does not exist", e);
            return null;
        }
        return resource;
    }

    /*
     * Returns a byte stream of a target resource from users home folder
     * @param resourcePath Path to the resource, no slash at the begining is required
     *
     * @return InputStream
     */
    public static InputStream getStreamFromUserspace(String resourcePath) {
        InputStream resource;
        try {
            if (OSUtils.isWindows() || OSUtils.isLinux() || OSUtils.isMac()) {
                resource = new FileInputStream(System.getProperty("user.home") + File.separator + resourcePath);
            } else { //Probably unix-like
                resource = new FileInputStream("/home/" + System.getProperty("user.name") + File.separator + resourcePath);
            }
            Bootstrap.logger.debug("Getting resource from: " + System.getProperty("user.home") + File.separator + resourcePath);

        } catch (FileNotFoundException e) {
            Bootstrap.logger.warn("Requested resource does not exist", e);
            return null;
        }
        return resource;
    }
}

