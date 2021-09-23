package me.deftware.client.framework.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.util.Session;

/**
 * @author Deftware
 */
@AllArgsConstructor
public enum AccountType {

	/**
	 * Legacy account with username and password
	 */
	Legacy(Session.AccountType.LEGACY),

	/**
	 * Mojang account email and password account
	 */
	Mojang(Session.AccountType.MOJANG),

	/**
	 * Microsoft account login
	 */
	Microsoft(Session.AccountType.MSA);

	@Getter
	private final Session.AccountType type;

}
