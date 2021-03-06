package me.deftware.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.deftware.client.framework.event.events.EventRenderPlayerModel;
import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.TextFormatting;

@Mixin(RenderLivingBase.class)
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> {

	@Inject(method = "isVisible", at = @At("HEAD"), cancellable = true)
	private void isVisible(T p_193115_1_, CallbackInfoReturnable<Boolean> ci) {
		EventRenderPlayerModel event = new EventRenderPlayerModel().send();
		if (event.isShouldRender()) {
			ci.setReturnValue(true);
		}
	}

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z) {
		GlStateManager.translate((float) x, (float) y, (float) z);
		String s = TextFormatting.getTextWithoutFormattingCodes(entityLivingBaseIn.getName());
		String names = (String) SettingsMap.getValue(SettingsMap.MapKeys.RENDER, "FLIP_USERNAMES", "");
		if (s != null && !names.equals("")) {
			boolean flip = false;
			if (names.contains(",")) {
				for (String name : names.split(",")) {
					if (name.equals(s)) {
						flip = true;
						break;
					}
				}
			} else {
				flip = names.equals(s);
			}
			if (flip) {
				GlStateManager.translate(0.0F, entityLivingBaseIn.height + 0.1F, 0.0F);
				GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
			}
		}
	}

}
