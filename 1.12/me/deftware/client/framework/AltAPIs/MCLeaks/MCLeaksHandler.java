package me.deftware.client.framework.AltAPIs.MCLeaks;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.deftware.client.framework.AltAPIs.MCLeaks.MCLeaks.MCLeaksSession;
import me.deftware.client.framework.Utils.WebUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class MCLeaksHandler {

	public static MCLeaksLoginStatus login(String token) {
		MCLeaksLoginStatus status = new MCLeaksLoginStatus(false, "");
		try {
			String url = MCLeaks.ENDPOINT + "redeem";
			String payload = "{\"token\":\"" + token + "\"}";
			String output = WebUtils.sendPostRequest(url, payload);

			JsonObject jsonObject = new Gson().fromJson(output, JsonObject.class);

			if (jsonObject.get("success").getAsString().trim().toLowerCase().equals("true")) {
				jsonObject = jsonObject.get("result").getAsJsonObject();

				String username = jsonObject.get("mcname").getAsString();
				String session = jsonObject.get("session").getAsString();

				MCLeaksSession s = new MCLeaksSession(username, session);

				MCLeaks.backupSession();

				MCLeaks.session = s;

				Session session2 = new Session(username, getCustomuserUUID(username), token, "legacy");

				Minecraft.getMinecraft().session = session2;

				status.setMessage("§aSuccess");
				status.setStatus(true);
			} else {
				status.setMessage("§c" + jsonObject.get("errorMessage").getAsString());
			}
		} catch (Exception ex) {
			status.setMessage("§cAn error occurred, please try again");
			ex.printStackTrace();
		}
		return status;
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
