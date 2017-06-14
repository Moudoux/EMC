package me.deftware.client.framework.Main;

import com.google.gson.JsonObject;

import net.minecraft.client.gui.GuiScreen;

public class GuiUpdateLoader extends GuiScreen {
	
	private JsonObject clientInfo;
	
	public GuiUpdateLoader(JsonObject clientInfo) {
		this.clientInfo = clientInfo;
	}
	
	private String get(String node) {
		return clientInfo.get(node).getAsString();
	}
	
	private int getInt(String node) {
		return clientInfo.get(node).getAsInt();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.drawDefaultBackground();
		
		this.drawCenteredString(this.fontRendererObj, "Update required", this.width / 2, 21,
				16777215);
		
		this.drawCenteredString(this.fontRendererObj, "Client info:", this.width / 2 - 110, 70,
				16777215);
		
		this.drawCenteredString(this.fontRendererObj, "Name: " + get("name"), this.width / 2 - 110, 90,
				16777215);
		
		this.drawCenteredString(this.fontRendererObj, "Version: " + getInt("version"), this.width / 2 - 110, 105,
				16777215);
		
		this.drawCenteredString(this.fontRendererObj, "Author: " + get("author"), this.width / 2 - 110, 120,
				16777215);
		
	}

}
