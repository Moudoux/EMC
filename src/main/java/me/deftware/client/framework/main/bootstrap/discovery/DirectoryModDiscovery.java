package me.deftware.client.framework.main.bootstrap.discovery;

import me.deftware.client.framework.main.bootstrap.Bootstrap;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class DirectoryModDiscovery extends AbstractModDiscovery {

    @Override
    public void discover() {
        Arrays.stream(Objects.requireNonNull(getDiscoverPath().listFiles())).forEach((file) -> {
            File deleteFile = new File(file.getAbsolutePath() + ".delete"),
                    updateFile = new File(file.getAbsolutePath() + ".update");
            if (!file.isDirectory() && file.getName().endsWith(".jar")) {
                if (deleteFile.exists()) {
                    Bootstrap.logger.info("Deleting {}", file.getName());
                    if (!deleteFile.delete() || !file.delete()) {
                        Bootstrap.logger.error("Failed to delete {}", file.getName());
                    }
                } else {
                    if (updateFile.exists()) {
                        if (!file.delete() || !updateFile.renameTo(file)) {
                            Bootstrap.logger.error("Failed to update {}", file.getName());
                        } else {
                            Bootstrap.logger.info("Updated {}", file.getName());
                        }
                    }
                    Bootstrap.logger.debug("Discovered {} with DirectoryModDiscovery", file.getName());
                    entries.add(new DirectoryModEntry(file, (String) null));
                }
            }
        });
    }

    @Override
    public File getDiscoverPath() {
        return Bootstrap.EMC_ROOT;
    }

    public static class DirectoryModEntry extends AbstractModEntry {

        public DirectoryModEntry(File file, String... data) {
            super(file, data);
        }

        @Override
        public void init() { }

    }

}
