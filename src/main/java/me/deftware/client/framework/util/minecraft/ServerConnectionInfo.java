package me.deftware.client.framework.util.minecraft;

import me.deftware.client.framework.chat.ChatMessage;
import net.minecraft.client.network.ServerInfo;

/**
 * @author Deftware
 */
public class ServerConnectionInfo extends ServerInfo {

	public ServerConnectionInfo(ServerInfo server) {
		super(server.name, server.address, server.isLocal());
		this.label = server.label;
		this.online = server.online;
		this.protocolVersion = server.protocolVersion;
		this.ping = server.ping;
		this.version = server.version;
		this.playerCountLabel = server.playerCountLabel;
		this.setIcon(server.getIcon());
	}

	public ServerConnectionInfo(String name, String ip, boolean isLan) {
		super(name, ip, isLan);
	}

	public ChatMessage getMotdAccessor() {
		return new ChatMessage().fromText(label);
	}

	public boolean isPingedAccessor() {
		return online;
	}

	public String getIPAccessor() {
		return address;
	}

	public boolean isLanServerAccessor () {
		return isLocal();
	}

	public String getServerNameAccessor() {
		return name;
	}

	public ChatMessage getGameVersionAccessor() {
		return new ChatMessage().fromText(version);
	}

	public int getVersionAccessor() {
		return protocolVersion;
	}

	public ChatMessage getPopulationInfoAccessor() {
		return new ChatMessage().fromText(playerCountLabel);
	}

	public long getPingToServerAccessor() {
		return ping;
	}

	public String getBase64EncodedIconDataAccessor() {
		return getIcon();
	}

	public void setBase64EncodedIconDataAccessor(String icon) {
		setIcon(icon);
	}

}
