package me.deftware.client.framework.main.bootstrap.discovery;

import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.utils.HashUtils;
import me.deftware.client.framework.utils.WebUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class JVMModDiscovery extends AbstractModDiscovery {

    @Override
    public void discover() {
        for (int i = 0; i < 100; i++) {
            if (System.getProperty("emcMod" + i) != null) {
                String[] mod = System.getProperty("emcMod" + i).split(",");
                File jar = new File(Bootstrap.EMC_ROOT.getAbsolutePath() + File.separator + mod[0] + ".jar"),
                        delete = new File(Bootstrap.EMC_ROOT.getAbsolutePath() + File.separator + mod[0] + ".jar.delete");
                if (!delete.exists()) {
                    Bootstrap.logger.debug("Discovered {} with JVMModDiscovery", jar.getName());
                    entries.add(new JVMModEntry(jar, mod));
                } else {
                    if (!delete.delete() || !jar.delete()) {
                        Bootstrap.logger.error("Failed to delete mod {}", jar.getName());
                    }
                }
            }
        }
    }

    public static class JVMModEntry extends AbstractModEntry {

        private String[] data;

        JVMModEntry(File file, String... data) {
            super(file, null);
            this.data = data;
        }

        @Override
        public void init() {
            String[] maven = data[1].split(":");
            String url = data[2] + maven[0].replace(".", "/") + "/" + maven[1] + "/" + maven[2] + "/" + maven[1] + "-" + maven[2] + ".jar", sha1 = url + ".sha1";
            try {
                if (!getFile().exists()) {
                    install(url);
                    Bootstrap.logger.info("Installed {}", getFile().getName());
                } else {
                    String remoteSHA1 = WebUtils.get(sha1);
                    try {
                        String hash = HashUtils.getSha1(getFile());
                        if (!remoteSHA1.equalsIgnoreCase(hash)) {
                            Bootstrap.logger.warn("SHA-1 not matching for {}, expected {} but got {}!", getFile().getName(), remoteSHA1, hash);
                            if (!getFile().delete()) {
                                Bootstrap.logger.error("Failed to delete {}", getFile().getName());
                            } else {
                                install(url);
                                Bootstrap.logger.info("Reinstalled {}", getFile().getName());
                            }
                        } else {
                            Bootstrap.logger.info("SHA-1 matching for {}", getFile().getName());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Bootstrap.logger.error("Unable to compute SHA-1 of {}", getFile().getName());
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Bootstrap.logger.error("Unable to install/verify {}", getFile().getName());
            }
        }

        @Override
        public EMCMod toInstance() throws Exception {
            return null;
        }

        private void install(String link) {
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL(link).openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
                connection.setRequestMethod("GET");
                FileOutputStream out = new FileOutputStream(getFile().getAbsolutePath());
                InputStream in = connection.getInputStream();
                int read;
                byte[] buffer = new byte[4096];
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                Bootstrap.logger.error("Failed to download {}", getFile().getName());
            }
        }

    }

}
