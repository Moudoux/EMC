package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventIsPotionActive;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.imp.IMixinEntityLivingBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase implements IMixinEntityLivingBase {

	@Shadow
	@Final
	private Map<Potion, PotionEffect> activePotionsMap;

	@Shadow
	private int activeItemStackUseCount;

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public boolean isPotionActive(Potion potionIn) {
		if (!((EntityLivingBase) (Object) this instanceof EntityPlayerSP)) {
			return activePotionsMap.containsKey(potionIn);
		}
		EventIsPotionActive event = new EventIsPotionActive(potionIn.getName(),
				activePotionsMap.containsKey(potionIn)).send();
		return event.isActive();
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	protected float getJumpUpwardsMotion() {
		return (float) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "JUMP_HEIGHT", 0.42F);
	}

	@Override
	public int getActiveItemStackUseCount() {
		return activeItemStackUseCount;
	}

}
