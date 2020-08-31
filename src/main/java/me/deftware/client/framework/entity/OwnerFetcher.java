package me.deftware.client.framework.entity;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import me.deftware.client.framework.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Deftware, wagyourtail
 */
public class OwnerFetcher {

	public static ThreadLocal<OwnerFetcher> INSTANCE = ThreadLocal.withInitial(OwnerFetcher::new);

	private final LoadingCache<UUID, Owner> users = CacheBuilder.newBuilder()
			.maximumSize(100)
			.expireAfterWrite(5, TimeUnit.MINUTES)
			.build(new CacheLoader<UUID, Owner>() {
				public Owner load(UUID uuid) {
					return new Owner(uuid);
				}
			});

	@Nullable
	public Owner getOwner(@Nonnull UUID uuid) {
		try {
			return users.get(uuid);
		} catch (Exception ex) {
			return null;
		}
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
