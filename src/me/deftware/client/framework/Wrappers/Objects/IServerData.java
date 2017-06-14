package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.client.multiplayer.ServerData;

public class IServerData extends ServerData {

	public IServerData(String name, String ip, boolean isLan) {
		super(name, ip, isLan);
	}
	
	public String getMotd() {
		return this.serverMOTD;
	}
	
	public boolean isPinged() {
		return this.pinged;
	}
	
	public boolean isLanServer() {
		return this.isLanServer();
	}
	
	public String getServerList() {
		return this.getServerList();
	}
	
	public String getServerName() {
		return this.serverName;
	}
	
	public String getGameVersion() {
		return this.gameVersion;
	}
	
	public int getVersion() {
		return this.version;
	}
	
	public String getPopulationInfo() {
		return this.populationInfo;
	}
	
	public long getPingToServer() {
		return this.pingToServer;
	}
	
	public String getBase64EncodedIconData() {
		return this.serverIcon;
	}
	
	public void setBase64EncodedIconData(String icon) {
		this.serverIcon = icon;
	}

}
