package me.deftware.client.framework.Main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;

import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

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
		buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 120 + 12 - 30,
				"Update " + (clientInfo.get("updateLinkOverride").getAsBoolean() ? clientInfo.get("name").getAsString()
						: "EMC")));
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 144 + 12 - 30, "Cancel (Mod won't load)"));
	}

	@Override
	protected void actionPerformed(GuiButton clickedButton) throws IOException {
		if (clickedButton.id == 0) {
			try {
				String link = "https://github.com/Moudoux/EMC-Installer/releases";
				if (clientInfo.get("updateLinkOverride").getAsBoolean()) {
					link = clientInfo.get("website").getAsString();
				}
				Desktop.getDesktop().browse(new URL(link).toURI());
			} catch (Exception e) {
				;
			}
			Minecraft.getMinecraft().shutdown();
		}
		Minecraft.getMinecraft().displayGuiScreen(null);
		super.actionPerformed(clickedButton);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.drawDefaultBackground();

		this.drawCenteredString(this.fontRendererObj, "EMC update required", this.width / 2, 21, 16777215);

		this.drawCenteredString(this.fontRendererObj, "Client info:", this.width / 2 - 110, 70, 16777215);

		this.drawCenteredString(this.fontRendererObj, "Name: " + get("name"), this.width / 2 - 110, 90, 16777215);

		this.drawCenteredString(this.fontRendererObj, "Version: " + getDouble("version"), this.width / 2 - 110, 105,
				16777215);

		this.drawCenteredString(this.fontRendererObj, "Author: " + get("author"), this.width / 2 - 110, 120, 16777215);

		// Right side

		this.drawCenteredString(this.fontRendererObj, "You need to update:", this.width / 2 + 70, 70, 16777215);

		this.drawCenteredString(this.fontRendererObj, "Your EMC version is too low.", this.width / 2 + 70, 90,
				16777215);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

}
