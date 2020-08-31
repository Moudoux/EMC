package me.deftware.client.framework.entity;

import lombok.Getter;
import me.deftware.client.framework.world.World;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Deftware, wagyourtail
 */
public class OwnerFetcher {

	public static ThreadLocal<OwnerFetcher> INSTANCE = ThreadLocal.withInitial(OwnerFetcher::new);

	private final ConcurrentHashMap<UUID, Owner> users = new ConcurrentHashMap<>();

	public Owner getOwner(@Nonnull UUID uuid) {
		if (users.containsKey(uuid)) return users.get(uuid);
		Owner owner = new Owner(uuid);
		users.put(uuid, owner);
		return owner;
	}

	public static class Owner {

		private @Getter boolean available = false;
		private String name = null;
		private final UUID uuid;

		public Owner(UUID uuid) {
			this.uuid = uuid;
			World.getUsernameFromUUID(uuid).thenAccept(username -> {
				this.name = username;
				this.available = username != null && !username.isEmpty();
			});
		}

		public String getName() {
			return name;
		}

	}

}
