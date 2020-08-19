package me.deftware.client.framework.minecraft;

import net.minecraft.client.options.Perspective;

/**
 * @author Deftware
 */
public enum PlayerPerspective {

	FIRST_PERSON(Perspective.FIRST_PERSON),
	THIRD_PERSON_BACK(Perspective.THIRD_PERSON_BACK),
	THIRD_PERSON_FRONT(Perspective.THIRD_PERSON_FRONT);

	private final Perspective perspective;

	PlayerPerspective(Perspective perspective) {
		this.perspective = perspective;
	}

	public Perspective getMinecraftPerspective() {
		return perspective;
	}

	public boolean isThirdPerson() {
		return this == THIRD_PERSON_BACK || this == THIRD_PERSON_FRONT;
	}

}
