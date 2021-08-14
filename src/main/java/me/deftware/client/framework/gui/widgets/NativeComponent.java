package me.deftware.client.framework.gui.widgets;

/**
 * A class holding a component interface,
 * emulating an implementation
 *
 * @since 17.0.0
 * @author Deftware
 */
public interface NativeComponent<T extends Component> {

	/**
	 * @return The component
	 */
	T getComponent();

}
