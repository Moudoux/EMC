package me.deftware.client.framework.util.minecraft;

import net.minecraft.util.Identifier;

/**
 * @author Deftware
 */
public class MinecraftIdentifier extends Identifier {

	public MinecraftIdentifier(Identifier identifier) {
		super(identifier.getNamespace(), identifier.getPath());
	}

	public MinecraftIdentifier(String id) {
		super(id);
	}

	public MinecraftIdentifier(String namespace, String path) {
		super(namespace, path);
	}

}
