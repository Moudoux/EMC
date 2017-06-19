package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.client.multiplayer.ServerData;

public class IServerData extends ServerData {

	public IServerData(String name, String ip, boolean isLan) {
		super(name, ip, isLan);
	}
	
	public String getIMotd() {
		return this.serverMOTD;
	}
	
	public boolean isIPinged() {
		return this.pinged;
	}
	
	public String getIIP() {
		return this.serverIP;
	}

	public boolean isILanServer() {
		return this.isOnLAN();
	}

	public String getIServerName() {
		return this.serverName;
	}
	
	public String getIGameVersion() {
		return this.gameVersion;
	}
	
	public int getIVersion() {
		return this.version;
	}
	
	public String getIPopulationInfo() {
		return this.populationInfo;
	}
	
	public long getIPingToServer() {
		return this.pingToServer;
	}
	
	public String getIBase64EncodedIconData() {
		return this.getBase64EncodedIconData();
	}
	
	public void setIBase64EncodedIconData(String icon) {
		this.setBase64EncodedIconData(icon);
	}

}
