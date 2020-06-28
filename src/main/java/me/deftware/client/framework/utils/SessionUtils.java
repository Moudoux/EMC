package me.deftware.client.framework.utils;

import me.deftware.client.framework.utils.session.AuthLibSession;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

public class SessionUtils {

    /**
     * Log into a Minecraft account
     *
     * @param username Email address for mojang account and username for legacy Minecraft account
     * @param password Password for Mojang Account
     * @return Whether the login was successfull
     */
    public static boolean loginWithPassword(String username, String password) {
        AuthLibSession session = new AuthLibSession();
        session.setCredentials(username, password);
        if (!session.login()) {
            return false;
        }
        session.setSession();
        return true;
    }

    /**
     * Set your Minecraft username, non premium
     *
     * @param username The username to login with
     */
    public static void loginWithoutPassword(String username) {
        ((IMixinMinecraft) MinecraftClient.getInstance()).setSession(new Session(username, "", "0", "legacy"));
    }

}
