package me.deftware.client.framework.main.bootstrap.discovery;

import com.google.gson.JsonObject;
import me.deftware.client.framework.main.EMCMod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Deftware
 */
public abstract class AbstractModDiscovery {

    List<AbstractModEntry> entries = new ArrayList<>();

    public abstract void discover();

    public Stream<AbstractModEntry> getMods() {
        return entries.stream();
    }

    public int getSize() {
        return entries.size();
    }

    public abstract static class AbstractModEntry {

        JsonObject json;
        private final File file;

        AbstractModEntry(File file, JsonObject json) {
            this.file = file;
            this.json = json;
        }

        public abstract void init();

        public abstract EMCMod toInstance() throws Exception;

        public File getFile() {
            return file;
        }

        public JsonObject getJson() {
            return json;
        }

    }

}
