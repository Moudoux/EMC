package me.deftware.client.framework.wrappers;


import me.deftware.client.framework.chat.ChatMessage;
import net.minecraft.client.network.ServerInfo;

public class IServerData extends ServerInfo {

    public IServerData(String name, String ip, boolean isLan) {
        super(name, ip, isLan);
    }

    public ChatMessage getIMotd() {
        return new ChatMessage().fromText(label);
    }

    public boolean isIPinged() {
        return online;
    }

    public String getIIP() {
        return address;
    }

    public boolean isILanServer() {
        return isLocal();
    }

    public String getIServerName() {
        return name;
    }

    public ChatMessage getIGameVersion() {
        return new ChatMessage().fromText(version);
    }

    public int getIVersion() {
        return protocolVersion;
    }

    public ChatMessage getIPopulationInfo() {
        return new ChatMessage().fromText(playerCountLabel);
    }

    public long getIPingToServer() {
        return ping;
    }

    public String getIBase64EncodedIconData() {
        return getIcon();
    }

    public void setIBase64EncodedIconData(String icon) {
        setIcon(icon);
    }

}
