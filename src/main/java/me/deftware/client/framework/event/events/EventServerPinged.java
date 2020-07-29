package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

/**
 * Triggered by Minecraft server list gui when server is being pinged.
 * This event includes the info about server like: MOTD, IP address, Servers' game varsion, Servers' population info and ping delay
 */
public class EventServerPinged extends Event {

	private String serverMOTD, playerList, gameVersion, populationInfo;
	private int version;
	private long pingToServer;

	public EventServerPinged(String serverMOTD, String playerList, String gameVersion, String populationInfo, int version, long pingToServer) {
		this.serverMOTD = serverMOTD;
		this.playerList = playerList;
		this.gameVersion = gameVersion;
		this.populationInfo = populationInfo;
		this.version = version;
		this.pingToServer = pingToServer;
	}

	public String getServerMOTD() {
		return serverMOTD;
	}

	public void setServerMOTD(String serverMOTD) {
		this.serverMOTD = serverMOTD;
	}

	public String getPlayerList() {
		return playerList;
	}

	public void setPlayerList(String playerList) {
		this.playerList = playerList;
	}

	public String getGameVersion() {
		return gameVersion;
	}

	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}

	public String getPopulationInfo() {
		return populationInfo;
	}

	public void setPopulationInfo(String populationInfo) {
		this.populationInfo = populationInfo;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long getPingToServer() {
		return pingToServer;
	}

	public void setPingToServer(long pingToServer) {
		this.pingToServer = pingToServer;
	}

}
