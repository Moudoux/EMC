package me.deftware.client.framework.main;

import com.google.gson.JsonObject;
import me.deftware.client.framework.wrappers.gui.IGuiButton;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * This class describes the gui shown by the EMC freamwork when 
 * installed version is too low and update is necessary 
 */

public class GuiUpdateLoader extends GuiScreen {

	private JsonObject clientInfo;

	public GuiUpdateLoader(JsonObject clientInfo) {
		this.clientInfo = clientInfo;
	}

	private String get(String node) {
		return clientInfo.get(node).getAsString();
	}

	private int getDouble(String node) {
		return clientInfo.get(node).getAsInt();
	}

	@Override
	public void initGui() {
		buttonList.clear();
		addButton(new IGuiButton(0, width / 2 - 100, height / 4 + 120 + 12 - 30,
				"Update " + (clientInfo.get("updateLinkOverride").getAsBoolean() ? clientInfo.get("name").getAsString()
						: "EMC")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				String link = "https://gitlab.com/EMC-Framework/EMC-Installer/tags";
				if (clientInfo.get("updateLinkOverride").getAsBoolean()) {
					link = clientInfo.get("website").getAsString();
				}
				IGuiScreen.openLink(link);
				Minecraft.getMinecraft().shutdown();
			}
		});
		addButton(new IGuiButton(1, width / 2 - 100, height / 4 + 144 + 12 - 30, "Cancel (Mod won't load)") {
			@Override
			public void onClick(double mouseX, double mouseY) {
				Minecraft.getMinecraft().displayGuiScreen(null);
			}
		});
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawDefaultBackground();

		drawCenteredString(fontRenderer, "EMC update required", width / 2, 21, 16777215);

		drawCenteredString(fontRenderer, "Client info:", width / 2 - 110, 70, 16777215);

		drawCenteredString(fontRenderer, "Name: " + get("name"), width / 2 - 110, 90, 16777215);

		drawCenteredString(fontRenderer, "Version: " + getDouble("version"), width / 2 - 110, 105,
				16777215);

		drawCenteredString(fontRenderer, "Author: " + get("author"), width / 2 - 110, 120, 16777215);

		// Right side

		drawCenteredString(fontRenderer, "You need to update:", width / 2 + 70, 70, 16777215);

		drawCenteredString(fontRenderer, "Your EMC version is too low.", width / 2 + 70, 90,
				16777215);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
