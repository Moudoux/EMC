package me.deftware.client.framework.utils;

import net.minecraft.client.Minecraft;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class HashUtils {


	public static String getSHA(String string) throws Exception {
		MessageDigest sha1 = MessageDigest.getInstance("SHA-512");
		sha1.update(string.getBytes());
		return new HexBinaryAdapter().marshal(sha1.digest());
	}


	public static String calcSHA(File file) throws Exception {
		MessageDigest sha1 = MessageDigest.getInstance("SHA-512");
		InputStream input = new FileInputStream(file);
		try {
			byte[] buffer = new byte[8192];
			int len = input.read(buffer);
			while (len != -1) {
				sha1.update(buffer, 0, len);
				len = input.read(buffer);
			}
			input.close();
			return new HexBinaryAdapter().marshal(sha1.digest());
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getClientHash() {
		try {
			File f = new File(Minecraft.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			return HashUtils.calcSHA(f);
		} catch (Exception ex) {
			return "";
		}
	}

}
