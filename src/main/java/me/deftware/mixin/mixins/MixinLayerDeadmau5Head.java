package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LayerDeadmau5Head.class)
public class MixinLayerDeadmau5Head {

	@Shadow
	@Final
	private RenderPlayer playerRenderer;

	/**
	 * @Author Deftware
	 * @reason
	 */
	@Overwrite
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		String usernames = (String) SettingsMap.getValue(SettingsMap.MapKeys.MISC, "DEADMAU_EARS", "");
		boolean flag = entitylivingbaseIn.getGameProfile().getName().equalsIgnoreCase(usernames);
		if (usernames.contains(",")) {
			for (String username : usernames.split(",")) {
				if (username.equalsIgnoreCase(entitylivingbaseIn.getGameProfile()
						.getName())) {
					flag = true;
					break;
				}
			}
		}
		if (entitylivingbaseIn.hasSkin() && !entitylivingbaseIn.isInvisible() && flag) {
			playerRenderer.bindTexture(entitylivingbaseIn.getLocationSkin());
			for (int i = 0; i < 2; ++i) {
				float f = entitylivingbaseIn.prevRotationYaw + (entitylivingbaseIn.rotationYaw - entitylivingbaseIn.prevRotationYaw) * partialTicks - (entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks);
				float f1 = entitylivingbaseIn.prevRotationPitch + (entitylivingbaseIn.rotationPitch - entitylivingbaseIn.prevRotationPitch) * partialTicks;
				GlStateManager.pushMatrix();
				GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(f1, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0.375F * (float) (i * 2 - 1), 0.0F, 0.0F);
				GlStateManager.translate(0.0F, -0.375F, 0.0F);
				GlStateManager.rotate(-f1, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(-f, 0.0F, 1.0F, 0.0F);
				float f2 = 1.3333334F;
				GlStateManager.scale(1.3333334F, 1.3333334F, 1.3333334F);
				playerRenderer.getMainModel().renderDeadmau5Head(0.0625F);
				GlStateManager.popMatrix();
			}
		}
	}

}
