package me.deftware.client.framework.render.camera;

import me.deftware.client.framework.math.vector.Vector3d;

/**
 * @author Deftware
 */
public interface GameCamera {
	
	Vector3d getCameraPosition();

	float _getRotationPitch();

	float _getRotationYaw();

	double _getRenderPosX();

	double _getRenderPosY();

	double _getRenderPosZ();

}
