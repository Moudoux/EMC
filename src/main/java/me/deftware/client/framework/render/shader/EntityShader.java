package me.deftware.client.framework.render.shader;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Deftware
 */
public class EntityShader {

	private static @Getter @Setter boolean active = false, storage = false;
	private static @Getter @Setter Shader shader;

	public static boolean shouldRun() {
		return active && shader != null;
	}

}
