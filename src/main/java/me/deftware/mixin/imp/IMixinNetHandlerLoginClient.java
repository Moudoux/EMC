package me.deftware.mixin.imp;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.NetworkManager;

public interface IMixinNetHandlerLoginClient {

	NetworkManager getNetworkManager();

	GameProfile getGameProfile();

	void setGameProfile(GameProfile profile);

}
