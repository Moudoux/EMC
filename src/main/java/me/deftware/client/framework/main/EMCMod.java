package me.deftware.client.framework.main;

import com.google.gson.JsonObject;
import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.utils.Settings;

public abstract class EMCMod {

	private Settings settings;
	public JsonObject clientInfo;

	public void init(JsonObject json) {
		clientInfo = json;
		settings = new Settings();
		settings.initialize(json);
		initialize();
	}

	public abstract void initialize();

	public abstract EMCClientInfo getClientInfo();

	public abstract void onEvent(Event event);

	public void onMarketplaceAuth(boolean status) {

	}

	protected void disable() {
		Bootstrap.getClients().remove(clientInfo.get("name").getAsString());
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
