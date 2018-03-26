package me.deftware.client.framework.wrappers;

import net.minecraft.client.multiplayer.ServerData;

public class IServerData extends ServerData {

	public IServerData(String name, String ip, boolean isLan) {
		super(name, ip, isLan);
	}

	public String getIMotd() {
		return serverMOTD;
	}

	public boolean isIPinged() {
		return pinged;
	}

	public String getIIP() {
		return serverIP;
	}

	public boolean isILanServer() {
		return isOnLAN();
	}

	public String getIServerName() {
		return serverName;
	}

	public String getIGameVersion() {
		return gameVersion;
	}

	public int getIVersion() {
		return version;
	}

	public String getIPopulationInfo() {
		return populationInfo;
	}

	public long getIPingToServer() {
		return pingToServer;
	}

	public String getIBase64EncodedIconData() {
		return getBase64EncodedIconData();
	}

	public void setIBase64EncodedIconData(String icon) {
		setBase64EncodedIconData(icon);
	}

}
