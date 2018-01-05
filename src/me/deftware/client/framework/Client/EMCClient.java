package me.deftware.client.framework.Client;

import com.google.gson.JsonObject;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Main.FrameworkLoader;
import me.deftware.client.framework.Utils.Storage.Settings;

public abstract class EMCClient {

	private Settings settings;
	public JsonObject clientInfo;

	public void init(JsonObject json) {
		clientInfo = json;
		settings = new Settings();
		settings.initialize(json);
		this.initialize();
	}

	public abstract void initialize();

	public abstract EMCClientInfo getClientInfo();

	public abstract void onEvent(Event event);

	public void onMarketplaceAuth(boolean status) {

	}

	protected void disable() {
		FrameworkLoader.getClients().remove(clientInfo.get("name").getAsString());
	}

	public Settings getSettings() {
		return settings;
	}

	public void callMethod(String method, String caller) {

	}

	public static class EMCClientInfo {

		private String clientName, clientVersion;

		public EMCClientInfo(String clientName, String clientVersion) {
			this.clientName = clientName;
			this.clientVersion = clientVersion;
		}

		public String getClientName() {
			return clientName;
		}

		public String getClientVersion() {
			return clientVersion;
		}

	}

	public void onUnload() {
	}

}
