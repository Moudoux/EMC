package me.deftware.mixin.mixins.entity;

import me.deftware.client.framework.event.events.EventFovModifier;
import me.deftware.client.framework.event.events.EventSpectator;
import me.deftware.client.framework.global.GameCategory;
import me.deftware.client.framework.global.GameMap;
import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.util.HashUtils;
import me.deftware.mixin.imp.IMixinAbstractClientPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.texture.PlayerSkinTexture;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class MixinAbstractClientPlayer implements IMixinAbstractClientPlayer {

	@Shadow
	private PlayerListEntry cachedScoreboardEntry;

	@Unique
	private boolean capeLoaded = false;

	@Unique
	private Identifier capeIdentifier = null;

	@Inject(method = "isSpectator", at = @At(value = "TAIL"), cancellable = true)
	private void onIsSpectator(CallbackInfoReturnable<Boolean> cir) {
		EventSpectator event = new EventSpectator(cir.getReturnValue());
		cir.setReturnValue(event.isSpectator());
	}

	@ModifyVariable(method = "getSpeed", ordinal = 0, at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;getAttributeInstance(Lnet/minecraft/entity/attribute/EntityAttribute;)Lnet/minecraft/entity/attribute/EntityAttributeInstance;"))
	private float onGetSpeed(float fov) {
		EventFovModifier event = new EventFovModifier(fov);
		event.broadcast();
		return event.getFov();
	}

	@Inject(method = "getCapeTexture", at = @At("TAIL"), cancellable = true)
	public void onGetCapeTexture(CallbackInfoReturnable<Identifier> ci) {
		try {
			String uuid = ((AbstractClientPlayerEntity) (Object) this).getUuidAsString();
			String uidHash = HashUtils.getSHA(uuid.replace("-", "")).toLowerCase();
            String id = GameMap.INSTANCE.contains(GameCategory.CapeTexture, ((AbstractClientPlayerEntity) (Object) this).getGameProfile().getName())
		            ? ((AbstractClientPlayerEntity) (Object) this).getGameProfile().getName() : GameMap.INSTANCE.contains(GameCategory.CapeTexture, uuid.replace("-", ""))
                            ? uuid.replace("-", "") : GameMap.INSTANCE.contains(GameCategory.CapeTexture, uidHash) ? uidHash : null;
            if (id != null) {
            	if (capeLoaded) {
            		ci.setReturnValue(capeIdentifier);
	            } else {
		            capeIdentifier = new Identifier(String.format("capes/%s.png", uidHash));
		            PlayerSkinTexture playerSkinTexture = new PlayerSkinTexture(new File(String.format("%s/libraries/EMC/capes/%s.png", Minecraft.getMinecraftGame()._getGameDir().getAbsolutePath(), uidHash)),
				            GameMap.INSTANCE.get(GameCategory.CapeTexture, id, ""), DefaultSkinHelper.getTexture(), false, () -> {
		            	capeLoaded = true;
		            });
		            MinecraftClient.getInstance().getTextureManager().registerTexture(capeIdentifier, playerSkinTexture);
	            }
            }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public PlayerListEntry getPlayerNetworkInfo() {
		return cachedScoreboardEntry;
	}

}
