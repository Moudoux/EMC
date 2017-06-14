package me.deftware.client.framework.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import net.minecraft.client.Minecraft;

public class HashUtils {

	/**
	 * Returns the SHA-512 hash of a given string
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public static String getSHA(String string) throws Exception {
		MessageDigest sha1 = MessageDigest.getInstance("SHA-512");
		sha1.update(string.getBytes());
		return new HexBinaryAdapter().marshal(sha1.digest());
	}
	
	/**
	 * Returns the SHA-512 checksum of a give file
	 * @param file
	 * @return
	 * @throws Exception
	 */
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
			return calcSHA(f);
		} catch (Exception ex) {
			return "";
		}
	}

}
