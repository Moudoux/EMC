package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.util.Session;

public class ISession extends Session {

	public ISession(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn) {
		super(usernameIn, playerIDIn, tokenIn, sessionTypeIn);
	}
	
	public String getSessionID() {
		return "token:" + this.token + ":" + this.playerID;
	}

	public String getPlayerID() {
		return this.playerID;
	}

	public String getUsername() {
		return this.username;
	}

	public String getToken() {
		return this.token;
	}
	
}
