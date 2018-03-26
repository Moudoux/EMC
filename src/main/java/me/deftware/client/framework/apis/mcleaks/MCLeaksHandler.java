package me.deftware.client.framework.apis.mcleaks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
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

public class MCLeaksHandler {

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

				MCLeaks.MCLeaksSession s = new MCLeaks.MCLeaksSession(username, session);

				MCLeaks.backupSession();

				MCLeaks.session = s;

				Session session2 = new Session(username, MCLeaksHandler.getCustomuserUUID(username), token, "legacy");

				((IMixinMinecraft) Minecraft.getMinecraft()).setSession(session2);

				status.setMessage("�aSuccess");
				status.setStatus(true);
			} else {
				status.setMessage("�c" + jsonObject.get("errorMessage").getAsString());
			}
		} catch (Exception ex) {
			status.setMessage("�cAn error occurred, please try again");
			ex.printStackTrace();
		}
		return status;
	}

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

	public static String getCustomuserUUID(String username) {
		try {
			String uuidjson = WebUtils.get("https://api.mojang.com/users/profiles/minecraft/" + username);

			JsonObject jsonObject = new Gson().fromJson(uuidjson, JsonObject.class);

			return jsonObject.get("id").getAsString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static class MCLeaksLoginStatus {

		private boolean status;
		private String message;

		public MCLeaksLoginStatus(boolean status, String message) {
			this.status = status;
			this.message = message;
		}

		public boolean isStatus() {
			return status;
		}

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
