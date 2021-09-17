package me.deftware.client.framework.render;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.math.vector.Vector3d;

import java.util.List;

/**
 * @author Deftware
 */
public interface WorldEntityRenderer {

	List<Statue> getStatues();

	@Getter
	@AllArgsConstructor
	class Statue {

		protected Entity entity;
		protected Vector3d position;

	}

}
