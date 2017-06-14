package me.deftware.client.framework.Client;

import me.deftware.client.framework.Event.Event;
import me.deftware.client.framework.Main.FrameworkLoader;
import me.deftware.client.framework.Utils.Storage.Settings;

public abstract class Client {

	private Settings settings;
	
	/**
	 * Called internally
	 * 
	 * @param instance
	 */
	public void init() {
		settings = new Settings();
		settings.initialize();
		this.initialize();
	}
	
	public abstract void initialize();

	public abstract ClientInfo getClientInfo();
	
	public abstract void onEvent(Event event);
	
	public Settings getSettings() {
		return settings;
	}
	
	public static class ClientInfo {
		
		private int protocol;
		private String mcName, clientName, clientVersion;

		public ClientInfo(int protocol, String mcName, String clientName, String clientVersion) {
			this.protocol = protocol;
			this.mcName = mcName;
			this.clientName = clientName;
			this.clientVersion = clientVersion;
		}

		public int getProtocol() {
			return protocol;
		}

		public String getMcName() {
			return mcName;
		}

		public String getClientName() {
			return clientName;
		}

		public String getClientVersion() {
			return clientVersion;
		}
		
	}
	
}
