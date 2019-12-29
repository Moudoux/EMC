package me.deftware.client.framework.main.bootstrap.discovery;

import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.utils.WebUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JVMModDiscovery extends AbstractModDiscovery {

    @Override
    public void discover() {
        for (int i = 0; i < 100; i++) {
            if (System.getProperty("emcMod" + i) == null) {
                break;
            }
            String[] mod = System.getProperty("emcMod" + i).split(",");
            File jar = new File(getDiscoverPath().getAbsolutePath() + File.separator + mod[0] + ".jar"),
                    delete = new File(getDiscoverPath().getAbsolutePath() + File.separator + mod[0] + ".jar.delete");
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

    @Override
    public File getDiscoverPath() {
        return Bootstrap.EMC_ROOT;
    }

    public static class JVMModEntry extends AbstractModEntry {

        public JVMModEntry(File file, String... data) {
            super(file, data);
        }

        @Override
        public void init() {
            String[] maven = data[1].split(":");
            String url = data[2] + maven[0].replace(".", "/") + "/" + maven[1] + "/" + maven[2] + "/" + maven[1] + "-" + maven[2] + ".jar", sha1 = url + ".sha1";
            try {
                if (!file.exists()) {
                    install(url);
                    Bootstrap.logger.info("Installed {}", file.getName());
                } else {
                    String remoteSHA1 = WebUtils.get(sha1);
                    try {
                        String hash = getHash();
                        if (!remoteSHA1.equalsIgnoreCase(hash)) {
                            Bootstrap.logger.warn("SHA-1 not matching for {}, expected {} but got {}!", file.getName(), remoteSHA1, hash);
                            if (!file.delete()) {
                                Bootstrap.logger.error("Failed to delete {}", file.getName());
                            } else {
                                install(url);
                                Bootstrap.logger.info("Reinstalled {}", file.getName());
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Bootstrap.logger.error("Unable to compute SHA-1 of {}", file.getName());
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Bootstrap.logger.error("Unable to install/verify {}", file.getName());
            }
        }

        private String getHash() throws IOException {
            String sha1 = null;
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e1) {
                throw new IOException("Impossible to get SHA-1 digester", e1);
            }
            try (InputStream input = new FileInputStream(file);
                 DigestInputStream digestStream = new DigestInputStream(input, digest)) {
                while (digestStream.read() != -1) { }
                MessageDigest msgDigest = digestStream.getMessageDigest();
                sha1 = new HexBinaryAdapter().marshal(msgDigest.digest());
            }
            return sha1;
        }

        private void install(String link) {
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL(link).openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
                connection.setRequestMethod("GET");
                FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
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
                Bootstrap.logger.error("Failed to download {}", file.getName());
            }
        }

    }

}
