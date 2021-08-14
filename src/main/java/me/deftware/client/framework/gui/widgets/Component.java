package me.deftware.client.framework.gui.widgets;

/**
 * Represents a UI widgets basic properties
 *
 * @since 17.0.0
 * @author Deftware
 */
public interface Component extends GenericComponent {

	int getPositionX();

	int getPositionY();;

	int getComponentWidth();;

	int getComponentHeight();;

	boolean isActive();

	void setPositionX(int x);

	void setPositionY(int y);

	void setComponentWidth(int width);

	void setComponentHeight(int height);

	void setActive(boolean state);

	default void setPosition(int x, int y) {
		setPositionX(x);
		setPositionY(y);
	}

	default void setSize(int width, int height) {
		setComponentWidth(width);
		setComponentHeight(height);
	}

}
