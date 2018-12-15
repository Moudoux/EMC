package me.deftware.client.framework.command.argument;


import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ArgumentBuilder  {

	public static CompletableFuture<Suggestions> getSuggestions(ArrayList<String> options, SuggestionsBuilder builder) {
		String s = builder.getRemaining().toLowerCase(Locale.ROOT);
		for (String s1 : options) {
			if (s1.toLowerCase(Locale.ROOT).startsWith(s)) {
				builder.suggest(s1);
			}
		}
		return builder.buildFuture();
	}

}
