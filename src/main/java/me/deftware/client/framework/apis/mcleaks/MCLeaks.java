package me.deftware.client.framework.apis.mcleaks;

import me.deftware.client.framework.utils.WebUtils;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.apache.commons.lang3.StringEscapeUtils;

public class MCLeaks {

	public static String ENDPOINT = "https://auth.mcleaks.net/v1/";

	public static MCLeaksSession session = null;
	private static Session original = null;

	/**
	 * Called internally to override the default Minecraft join call
	 *
	 * @param session
	 * @param serverHash
	 * @param server
	 * @return boolean
	 */
	public static boolean joinServer(MCLeaksSession session, String serverHash, String server) {
		if (session != null) {
			try {
				String url = MCLeaks.ENDPOINT + "joinserver";
				String payload = "{\"session\":\"" + session.getSession() + "\",\"mcname\":\"" + session.getMcname()
						+ "\",\"serverhash\":\"" + serverHash + "\",\"server\":\"" + server + "\"}";
				WebUtils.post(url, StringEscapeUtils.unescapeJava(payload));
				return true;
			} catch (Exception ex) {
			}
		}
		return false;
	}

	/**
	 * If the original session before MCLeaks has been stored
	 *
	 * @return boolean
	 */
	public static boolean isOriginalSessionSet() {
		if (MCLeaks.original == null) {
			return false;
		}
		return true;
	}

	/**
	 * Backs up the current session so the user can go back to their original account after using MCLeaks
	 */
	public static void backupSession() {
		if (MCLeaks.original == null) {
			MCLeaks.original = Minecraft.getInstance().getSession();
		}
	}

	/**
	 * Removes the MCLeaks session
	 */
	public static void clearMCLeaksSession() {
		MCLeaks.original = null;
		MCLeaks.session = null;
	}

	/**
	 * Restores the default Minecraft session used before using MCLeaks
	 */
	public static void restoreSession() {
		((IMixinMinecraft) Minecraft.getInstance()).setSession(MCLeaks.original);
		MCLeaks.clearMCLeaksSession();
	}

	public static class MCLeaksSession {

		private String mcname, session;

		public MCLeaksSession(String mcname, String session) {
			this.session = session;
			this.mcname = mcname;
		}

		public String getMcname() {
			return mcname;
		}

		public String getSession() {
			return session;
		}

	}

}
