package me.deftware.mixin.imp;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.ClientConnection;

public interface IMixinNetHandlerLoginClient {

    ClientConnection getNetworkManager();

    GameProfile getGameProfile();

    void setGameProfile(GameProfile profile);

}
