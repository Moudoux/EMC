package me.deftware.client.framework.wrappers.gui;

import java.util.ArrayList;
import java.util.List;

public class DebugHudWrapper {

	public static List<StringModifier> modifiers = new ArrayList<>();

	@FunctionalInterface
	public interface StringModifier {

		List<String> apply(List<String> data);

	}

}
