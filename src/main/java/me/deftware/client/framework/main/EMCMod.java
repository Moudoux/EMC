package me.deftware.client.framework.main;

import com.google.gson.JsonObject;
import me.deftware.client.framework.utils.Settings;

/**
 * This is a parent class for all of the mods loaded by EMC.
 * Your mod must extend this class
 */
public abstract class EMCMod {

    private Settings settings;
    public JsonObject modInfo;

    protected void init(JsonObject json) {
        modInfo = json;
        settings = new Settings();
        settings.initialize(json);
        initialize();
    }

    /**
     * Called before any events are sent to your mod, do your initialization here
     */
    public abstract void initialize();

    /**
     * Unloads your mod from EMC
     */
    protected void disable() {
        Bootstrap.getMods().remove(modInfo.get("name").getAsString());
    }

    /**
     * Returns your main EMC mod settings handler
     *
     * @return Settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Called when Minecraft is closed, use this method to save anything in your mod
     */
    public void onUnload() { }

    /**
     * By implementing this function you can call functions in other EMC mods
     *
     * @param method The method the caller wants to call
     * @param caller The EMC mod that is calling your function
     */
    public void callMethod(String method, String caller, Object object) { }

    public void postInit() { }

}
