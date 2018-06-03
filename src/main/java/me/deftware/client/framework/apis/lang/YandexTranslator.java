package me.deftware.client.framework.apis.lang;

import com.github.vbauer.yta.model.Language;
import com.github.vbauer.yta.service.YTranslateApiImpl;

import java.util.Map;

public class YandexTranslator {

	private static YTranslateApiImpl api;

	public static void init(String key) {
		api = new YTranslateApiImpl(key);
	}

	public static String translate(String source, Language to) {
		return api.translationApi().translate(source, to).text();
	}

	public static Language stringToLanguage(String lang) throws Exception {
		for (Map.Entry<String, Language> entry : Language.ALL.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(lang)) {
				return entry.getValue();
			}
		}
		throw new Exception("Language not found");
	}

}
