package me.deftware.client.framework.wrappers;

import net.minecraft.client.options.ServerEntry;

public class IServerData extends ServerEntry {

    public IServerData(String name, String ip, boolean isLan) {
        super(name, ip, isLan);
    }

    public String getIMotd() {
        return label;
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
        return version;
    }

    public int getIVersion() {
        return protocolVersion;
    }

    public String getIPopulationInfo() {
        return playerCountLabel;
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
