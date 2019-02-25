package me.deftware.client.framework.main;

import com.google.gson.JsonObject;
import me.deftware.client.framework.wrappers.gui.IGuiButton;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

/**
 * This class describes the gui shown by the EMC framework when
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
		buttons.clear();
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
				Minecraft.getInstance().shutdown();
			}
		});
		addButton(new IGuiButton(1, width / 2 - 100, height / 4 + 144 + 12 - 30, "Cancel (Mod won't load)") {
			@Override
			public void onClick(double mouseX, double mouseY) {
				Minecraft.getInstance().displayGuiScreen(null);
			}
		});
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);
		drawDefaultBackground();

		drawCenteredString(fontRenderer, "EMC update required", width / 2, 21, 16777215);

		drawCenteredString(fontRenderer, "Client info:", width / 4, 70, 16777215);

		drawCenteredString(fontRenderer, "Name: " + get("name"), width / 4, 90, 16777215);

		drawCenteredString(fontRenderer, "Version: " + getDouble("version"), width / 4, 105,
				16777215);

		drawCenteredString(fontRenderer, "Author: " + get("author"), width / 4, 120, 16777215);

		// Right side

		drawCenteredString(fontRenderer, "Problem description:", width / 2 + width / 4, 70, 16777215);

		drawCenteredString(fontRenderer, "Your EMC version is too low", width / 2 + width / 4, 90,
				16777215);

		drawCenteredString(fontRenderer, get("name") + " requires " + get("minversion"), width / 2 + width / 4, 105,
				16777215);

		super.render(mouseX, mouseY, partialTicks);
	}

}
