package me.deftware.client.framework.util;

import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.util.path.OSUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Deftware
 */
public class ResourceUtils {

    /**
     * Returns a byte stream of a target resource
     * @param mod Native name of the mod the resource corresponds to (name from client.json
     * @param resourcePath Path to the resource, no slash at the beginning is required
     *
     * @return InputStream
     */
    public static InputStream getStreamFromModResources(EMCMod mod, String resourcePath) {
        InputStream in;
        try {
            ZipFile zipFile = new ZipFile(mod.physicalFile);
            ZipEntry entry = zipFile.getEntry(resourcePath);
            in = zipFile.getInputStream(entry);
        } catch (Exception e) {
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
            Bootstrap.logger.debug("Getting resource from: " + Minecraft.getMinecraftGame()._getGameDir() + File.separator + resourcePath);
            resource = new FileInputStream(Minecraft.getMinecraftGame()._getGameDir() + File.separator + resourcePath);
        } catch (Exception e) {
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
        } catch (Exception e) {
            Bootstrap.logger.error("Requested resource does not exist", e);
            return null;
        }
        return resource;
    }

}

