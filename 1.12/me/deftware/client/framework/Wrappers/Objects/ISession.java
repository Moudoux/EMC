package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.util.Session;

public class ISession extends Session {

	public ISession(String usernameIn, String playerIDIn, String tokenIn, String sessionTypeIn) {
		super(usernameIn, playerIDIn, tokenIn, sessionTypeIn);
	}

	public String getISessionID() {
		return this.getSessionID();
	}

	public String getIPlayerID() {
		return this.getPlayerID();
	}

	public String getIUsername() {
		return this.getUsername();
	}

	public String getIToken() {
		return this.getToken();
	}

}
