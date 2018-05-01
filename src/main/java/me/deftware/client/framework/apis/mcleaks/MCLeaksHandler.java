package me.deftware.client.framework.apis.mcleaks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.deftware.client.framework.utils.ChatColor;
import me.deftware.client.framework.utils.WebUtils;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.util.CryptManager;
import net.minecraft.util.Session;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.PublicKey;

/**
 * API for using an MCLeaks account
 */
public class MCLeaksHandler {

	/**
	 * Replaces the Minecraft session with an MCLeaks session
	 *
	 * @param token The token retrieved from mcleaks.net
	 * @return {@link MCLeaksLoginStatus}
	 */
	public static MCLeaksLoginStatus login(String token) {
		MCLeaksLoginStatus status = new MCLeaksLoginStatus(false, "");
		try {
			String url = MCLeaks.ENDPOINT + "redeem";
			String payload = "{\"token\":\"" + token + "\"}";
			String output = WebUtils.post(url, payload);

			JsonObject jsonObject = new Gson().fromJson(output, JsonObject.class);

			if (jsonObject.get("success").getAsString().trim().toLowerCase().equals("true")) {
				jsonObject = jsonObject.get("result").getAsJsonObject();
				String username = jsonObject.get("mcname").getAsString();
				String session = jsonObject.get("session").getAsString();

				MCLeaks.MCLeaksSession s = MCLeaks.session = new MCLeaks.MCLeaksSession(username, session);
				MCLeaks.backupSession();

				((IMixinMinecraft) Minecraft.getMinecraft()).setSession(new Session(username, MCLeaksHandler.getCustomuserUUID(username), token, "legacy"));

				status.setMessage(ChatColor.GREEN + "Success");
				status.setStatus(true);
			} else {
				status.setMessage(ChatColor.RED + jsonObject.get("errorMessage").getAsString());
			}
		} catch (Exception ex) {
			status.setMessage(ChatColor.RED + "An error occurred, please try again");
			ex.printStackTrace();
		}
		return status;
	}

	/**
	 * Used internally, replaces the default Minecraft encryption request
	 *
	 * @param packetIn
	 * @param networkManager
	 */
	public static void handleEncryptionRequest(SPacketEncryptionRequest packetIn, NetworkManager networkManager) {
		SecretKey secretkey = CryptManager.createNewSharedKey();
		String s = packetIn.getServerId();
		PublicKey publickey = packetIn.getPublicKey();
		String s1 = (new BigInteger(CryptManager.getServerIdHash(s, publickey, secretkey))).toString(16);
		MCLeaks.joinServer(MCLeaks.session, s1, Minecraft.getMinecraft().getCurrentServerData().serverIP);
		networkManager.sendPacket(new CPacketEncryptionResponse(secretkey, publickey, packetIn.getVerifyToken()),
				new GenericFutureListener<Future<? super Void>>() {
					@Override
					public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
						networkManager.enableEncryption(secretkey);
					}
				});
	}

	/**
	 * Returns the UUID of any Minecraft username from the Mojang API
	 *
	 * @param username
	 * @return
	 */
	public static String getCustomuserUUID(String username) throws Exception {
			return  new Gson().fromJson(WebUtils.get("https://api.mojang.com/users/profiles/minecraft/" + username)
					, JsonObject.class).get("id").getAsString();
	}

	/**
	 * MCLeaks session object
	 */
	public static class MCLeaksLoginStatus {

		private boolean status;
		private String message;

		public MCLeaksLoginStatus(boolean status, String message) {
			this.status = status;
			this.message = message;
		}

		/**
		 * If the session is valid
		 *
		 * @return
		 */
		public boolean isStatus() {
			return status;
		}

		/**
		 * Any error message if the connection failed
		 *
		 * @return
		 */
		public String getMessage() {
			return message;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

}
