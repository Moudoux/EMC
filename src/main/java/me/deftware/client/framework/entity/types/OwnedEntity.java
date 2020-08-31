package me.deftware.client.framework.entity.types;

import me.deftware.client.framework.entity.OwnerFetcher;

import javax.annotation.Nullable;
import java.util.UUID;

public class OwnedEntity extends LivingEntity {

	protected final OwnerFetcher ownerFetcher = OwnerFetcher.INSTANCE.get();

	public OwnedEntity(net.minecraft.entity.Entity entity) {
		super(entity);
	}

	@Nullable
	public UUID getOwnerUUID() {
		return getNbt().getUUID("Owner");
	}

	@Nullable
	public String getOwnerUsername() {
		UUID uuid = getOwnerUUID();
		if (uuid != null) {
			OwnerFetcher.Owner owner = ownerFetcher.getOwner(uuid);
			if (owner != null && owner.isAvailable()) return owner.getName();
		}
		return null;
	}

}
