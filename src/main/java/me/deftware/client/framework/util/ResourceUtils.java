package me.deftware.client.framework.util;

import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.util.path.OSUtils;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Deftware
 */
public class ResourceUtils {

    /**
     * Returns a byte stream of a target resource
     * @param mod Native name of the mod the resource corresponds to (name from client.json
     * @param resourcePath Path to the resource, no slash at the begining is required
     *
     * @return InputStream
     */
    public static InputStream getStreamFromModResources(EMCMod mod, String resourcePath) {
        InputStream in;
        try {
            ZipFile zipFile = new ZipFile(mod.physicalFile);
            ZipEntry entry = zipFile.getEntry(resourcePath);
            in = zipFile.getInputStream(entry);
        } catch (IOException e) {
            Bootstrap.logger.error("Requested resource does not exist", e);
            return null;
        }
        return in;
    }

    /**
     * Returns a byte stream of a target resource from .minecraft folder
     * @param resourcePath Path to the resource, no slash at the begining is required
     *
     * @return InputStream
     */
    public static InputStream getStreamFromMinecraftResources(String resourcePath) {
        InputStream resource;
        try {
            Bootstrap.logger.debug("Getting resource from: " + Minecraft.getRunningLocation() + File.separator + resourcePath);
            resource = new FileInputStream(Minecraft.getRunningLocation() + File.separator + resourcePath);
        } catch (URISyntaxException | FileNotFoundException e) {
            Bootstrap.logger.error("Requested resource does not exist", e);
            return null;
        }
        return resource;
    }

    /**
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
            Bootstrap.logger.error("Requested resource does not exist", e);
            return null;
        }
        return resource;
    }

    /**
     * Returns a boolean of whether an external mod menu is present in this version
     *
     * @return Boolean depending on Mod Menu and MC Version/Modloader
     */
    public static boolean hasExternalModMenu() {
        return hasSpecificMod("modmenu");
    }

    /**
     * Returns a boolean of whether an external mod is present in this version
     *
     * @return Boolean depending on Mod ID and MC Version/Modloader
     */
    public static boolean hasSpecificMod(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}

