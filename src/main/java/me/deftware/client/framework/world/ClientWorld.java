package me.deftware.client.framework.world;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.minecraft.Minecraft;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * @since 17.0.0
 * @author Deftware
 */
public interface ClientWorld extends World {

	/**
	 * @return The in-game client world
	 */
	@Nullable
	static ClientWorld getClientWorld() {
		return Minecraft.getMinecraftGame().getClientWorld();
	}

	/**
	 * @return A stream of all entities
	 */
	Stream<Entity> getLoadedEntities();

	/**
	 * @param id Entity id
	 * @return The EMC entity associated with the ID
	 */
	Entity _getEntityById(int id);

	/**
	 * Adds a custom entity to the world
	 *
	 * @param id A unique entity ID
	 * @param entity An EMC entity
	 */
	void _addEntity(int id, Entity entity);

	/**
	 * Removes an entity from the world
	 *
	 * @param id Entity iD
	 */
	void _removeEntity(int id);

	/**
	 * Converts a Minecraft entity to an EMC entity
	 *
	 * @param reference A minecraft entity
	 * @return An EMC {@link Entity}
	 */
	@Nullable
	@ApiStatus.Internal
	<T extends Entity> T getEntityByReference(net.minecraft.entity.Entity reference);

}
