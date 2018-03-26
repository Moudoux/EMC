package me.deftware.mixin.mixins;

import com.mojang.authlib.GameProfile;
import me.deftware.client.framework.apis.mcleaks.MCLeaks;
import me.deftware.client.framework.apis.mcleaks.MCLeaksHandler;
import me.deftware.mixin.imp.IMixinNetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerLoginClient.class)
public class MixinNetHandlerLoginClient implements IMixinNetHandlerLoginClient {

	@Final
	@Shadow
	protected NetworkManager networkManager;

	@Shadow
	private GameProfile gameProfile;

	@Inject(method = "handleEncryptionRequest", at = @At("HEAD"), cancellable = true)
	private void handleEncryptionRequest(SPacketEncryptionRequest packetIn, CallbackInfo ci) {
		if (MCLeaks.session != null) {
			MCLeaksHandler.handleEncryptionRequest(packetIn, networkManager);
			ci.cancel();
		}
	}

	@Override
	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	@Override
	public GameProfile getGameProfile() {
		return gameProfile;
	}

	@Override
	public void setGameProfile(GameProfile profile) {
		gameProfile = profile;
	}
}
