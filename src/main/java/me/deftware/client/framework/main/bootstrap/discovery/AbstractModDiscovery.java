package me.deftware.client.framework.main.bootstrap.discovery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModDiscovery {

    protected List<AbstractModEntry> entries = new ArrayList<>();

    public abstract void discover();

    public abstract File getDiscoverPath();

    public List<AbstractModEntry> getMods() {
        return entries;
    }

    public abstract static class AbstractModEntry {

        public File file;
        protected String[] data;

        public AbstractModEntry(File file, String... data) {
            this.file = file;
            this.data = data;
        }

        public abstract void init();

    }

}
