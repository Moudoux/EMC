package me.deftware.mixin.mixins;

import com.mojang.authlib.GameProfile;
import me.deftware.mixin.imp.IMixinNetHandlerLoginClient;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientLoginNetworkHandler.class)
public class MixinNetHandlerLoginClient implements IMixinNetHandlerLoginClient {

    @Final
    @Shadow
    protected ClientConnection connection;

    @Shadow
    private GameProfile playerProfile;

    @Override
    public ClientConnection getNetworkManager() {
        return connection;
    }

    @Override
    public GameProfile getGameProfile() {
        return playerProfile;
    }

    @Override
    public void setGameProfile(GameProfile profile) {
        playerProfile = profile;
    }

}
