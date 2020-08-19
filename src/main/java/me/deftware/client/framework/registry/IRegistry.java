package me.deftware.client.framework.registry;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Deftware
 */
public interface IRegistry<Type, InternalType> {

	Optional<Type> find(String id);

	Stream<Type> stream();

	void register(String id, InternalType object);

}
