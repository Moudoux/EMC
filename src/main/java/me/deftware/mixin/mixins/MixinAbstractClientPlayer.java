package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventFovModifier;
import me.deftware.client.framework.event.events.EventSpectator;
import me.deftware.mixin.imp.IMixinAbstractClientPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer implements IMixinAbstractClientPlayer {

	@Shadow
	private NetworkPlayerInfo playerInfo;

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public boolean isSpectator() {
		NetworkPlayerInfo networkplayerinfo = Minecraft.getInstance().getConnection()
				.getPlayerInfo(((EntityPlayer) (Object) this).getGameProfile().getId());
		EventSpectator event = new EventSpectator(
				networkplayerinfo != null && networkplayerinfo.getGameType() == GameType.SPECTATOR).send();
		return event.isSpectator();
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public float getFovModifier() {
		float f = 1.0F;

		if (((AbstractClientPlayer) (Object) this).abilities.isFlying) {
			f *= 1.1F;
		}

		EventFovModifier event = new EventFovModifier(f).send();
		f = event.getFov();

		IAttributeInstance iattributeinstance = ((AbstractClientPlayer) (Object) this).getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		f = (float) ((double) f * ((iattributeinstance.getValue() / (double) ((AbstractClientPlayer) (Object) this).abilities.getWalkSpeed() + 1.0D) / 2.0D));

		if (((AbstractClientPlayer) (Object) this).abilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
			f = 1.0F;
		}

		if (((AbstractClientPlayer) (Object) this).isHandActive() && ((AbstractClientPlayer) (Object) this).getActiveItemStack().getItem() == Items.BOW) {
			int i = ((AbstractClientPlayer) (Object) this).getItemInUseMaxCount();
			float f1 = (float) i / 20.0F;

			if (f1 > 1.0F) {
				f1 = 1.0F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
		}

		return f;
	}

	@Override
	public NetworkPlayerInfo getPlayerNetworkInfo() {
		return playerInfo;
	}

}
