package me.deftware.client.framework.wrappers;


import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.client.network.ServerInfo;

public class IServerData extends ServerInfo {

    public IServerData(String name, String ip, boolean isLan) {
        super(name, ip, isLan);
    }

    public String getIMotd() {
        return ChatProcessor.getStringFromText(label);
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

    public String getIGameVersion() {
        return ChatProcessor.getStringFromText(version);
    }

    public int getIVersion() {
        return protocolVersion;
    }

    public String getIPopulationInfo() {
        return ChatProcessor.getStringFromText(playerCountLabel);
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
