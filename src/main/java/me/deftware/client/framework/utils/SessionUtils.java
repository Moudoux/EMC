package me.deftware.client.framework.utils;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Session;

import java.net.Proxy;
import java.util.UUID;

public class SessionUtils {

    /**
     * Log into a Minecraft account
     *
     * @param username Email address for mojang account and username for legacy Minecraft account
     * @param password
     * @return
     */
    public static boolean loginWithPassword(String username, String password) {
        Session session = null;
        UserAuthentication auth = new YggdrasilUserAuthentication(
                new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()), Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            String userName = auth.getSelectedProfile().getName();
            UUID playerUUID = auth.getSelectedProfile().getId();
            String accessToken = auth.getAuthenticatedToken();

            session = new Session(userName, playerUUID.toString(), accessToken,
                    username.contains("@") ? "mojang" : "legacy");

            ((IMixinMinecraft) MinecraftClient.getInstance()).setSession(session);

            return true;
        } catch (AuthenticationException e) {
        }
        return false;
    }

    /**
     * Set your Minecraft username, non premium
     *
     * @param username
     */
    public static void loginWithoutPassword(String username) {
        ((IMixinMinecraft) MinecraftClient.getInstance()).setSession(new Session(username, "", "0", "legacy"));
    }

}
