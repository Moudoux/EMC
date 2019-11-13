package me.deftware.client.framework.event.events;

import com.google.gson.JsonObject;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.utils.render.RenderUtils;
import me.deftware.client.framework.wrappers.IMinecraft;
import me.deftware.client.framework.wrappers.entity.IDummyEntity;
import me.deftware.client.framework.wrappers.entity.IEntityPlayer;
import me.deftware.client.framework.wrappers.render.IFontRenderer;
import me.deftware.client.framework.wrappers.render.IGlStateManager;
import me.deftware.client.framework.wrappers.render.IRenderHelper;
import me.deftware.client.framework.wrappers.render.IRenderManager;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.client.framework.wrappers.world.ICamera;

import java.awt.*;

/**
 * Triggered by Minecraft gui when three-dimensional things are being rendered.
 * <p>
 * Be careful! This event is triggered every frame so the rate is equal to FPS
 */
public class EventRender3D extends Event {

	private float partialTicks;

	public EventRender3D(float partialTicks) {
		this.partialTicks = partialTicks;
		Waypoint point = new Waypoint("Test", 200, 70, 200, "Test", Color.red.getRGB(), true);
		double x = point.getX() + 0.5F - IRenderManager.getRenderPosX();
		double y = point.getY() - IRenderManager.getRenderPosY();
		double z = point.getZ() + 0.5F - IRenderManager.getRenderPosZ();
		renderNameTag(point, x, y, z, partialTicks);
	}

	private void renderNameTag(Waypoint point, double x, double y, double z, float delta) {
		double originalPositionX = ICamera.getPosX();
		double originalPositionY = ICamera.getPosY();
		double originalPositionZ = ICamera.getPosZ();
		/*
		ICamera.setPosX(interpolate(ICamera.getPrevPosX(), ICamera.getPosX(), delta));
		ICamera.setPosX(interpolate(ICamera.getPrevPosY(), ICamera.getPosY(), delta));
		ICamera.setPosX(interpolate(ICamera.getPrevPosZ(), ICamera.getPosZ(), delta));
		*/
		int width = IFontRenderer.getStringWidth(point.getName()) / 2;
		double scale = 0.0245;
		IGlStateManager.pushMatrix();
		IRenderHelper.enableStandardItemLighting();
		IGlStateManager.enablePolygonOffset();
		IGlStateManager.doPolygonOffset(1.0F, -1500000.0F);
		IGlStateManager.disableLighting();
		IGlStateManager.translate((float) x, (float) y + 1.4F, (float) z);
		IGlStateManager.rotate(-IRenderManager.getPlayerViewY(), 0.0F, 1.0F, 0.0F);
		IGlStateManager.rotate(IRenderManager.getPlayerViewX(), IMinecraft.thridPersonView() == 2 ? -1.0F : 1.0F, 0.0F, 0.0F);
		IGlStateManager.scale(-scale, -scale, scale);
		IGlStateManager.disableDepth();
		IGlStateManager.enableBlend();
		IGlStateManager.disableAlpha();
		RenderUtils.drawBorderedRectReliant(-width - 2, -(IFontRenderer.getFontHeight() + 1), width + 2.0F, 1.0F, 1.8F, 1426064384, 855638016);
		IGlStateManager.enableAlpha();
		IFontRenderer.drawStringWithShadow(point.getName(), -width, -(IFontRenderer.getFontHeight() - 1), -1);
		//ICamera.setPosX(originalPositionX);
		//ICamera.setPosY(originalPositionY);
		//ICamera.setPosZ(originalPositionZ);
		IGlStateManager.enableDepth();
		IGlStateManager.enableLighting();
		IGlStateManager.disableBlend();
		IGlStateManager.enableLighting();
		IRenderHelper.disableStandardItemLighting();
		IGlStateManager.disablePolygonOffset();
		IGlStateManager.doPolygonOffset(1.0F, 1500000.0F);
		IGlStateManager.popMatrix();
		IBlockPos pos = new IBlockPos(point.getX() + 0.5F, point.getY() + 0.478F, point.getZ() + 0.5F);
		RenderUtils.tracerLine(new IDummyEntity(pos), new Color(point.getColor()));
	}

	public static class Waypoint {

		private int x, y, z, color;
		private String name, server;
		private boolean enabled;

		public Waypoint(JsonObject json) {
			this(json.get("name").getAsString(), json.get("x").getAsInt(), json.get("y").getAsInt(), json.get("z").getAsInt(), json.get("server").getAsString(), json.get("color").getAsInt(), json.get("enabled").getAsBoolean());
		}

		public Waypoint(String name, double x, double y, double z, String server, int color, boolean enabled) {
			this.name = name;
			this.x = (int) x;
			this.y = (int) y;
			this.z = (int) z;
			this.server = server;
			this.color = color;
			this.enabled = enabled;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZ() {
			return z;
		}

		public int getColor() {
			return color;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean state) {
			enabled = state;
		}

		public String getName() {
			return name;
		}

		public String getServer() {
			return server;
		}

		public JsonObject toJson() {
			JsonObject json = new JsonObject();
			json.addProperty("x", x);
			json.addProperty("y", y);
			json.addProperty("z", z);
			json.addProperty("color", color);
			json.addProperty("name", name);
			json.addProperty("enabled", enabled);
			json.addProperty("server", server);
			return json;
		}

	}

	private double interpolate(double previous, double current, float delta) {
		return previous + (current - previous) * delta;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

}
