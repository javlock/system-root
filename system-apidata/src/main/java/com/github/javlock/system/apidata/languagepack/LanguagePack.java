package com.github.javlock.system.apidata.languagepack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class LanguagePack {

	public final class LanguagePackKeys {
		public static final String FOUNDPATHTOSERVICE = "FOUND_PATH_TO_SERVICE";
		public static final String UPDATEGITREPO = "UPDATE_GIT_REPO";

		private LanguagePackKeys() {
		}
	}

	public static class LanguagePackValue {
		private ConcurrentHashMap<String, String> langValueMap = new ConcurrentHashMap<>();

		public String getForLang(String language) {
			return langValueMap.get(language);
		}

		public void setForLang(String lang, String value) {
			langValueMap.put(lang, value);
		}

	}

	private static String language;
	private static String languageDefault = "en";

	private static Locale locale;
	private static ConcurrentHashMap<String, LanguagePackValue> stringsMap = new ConcurrentHashMap<>();
	static {
		try {
			locale = Locale.getDefault();
			language = locale.getLanguage();
			readLanguagePackFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// <key , LanguagePackValue>

	private static InputStream getFileAsIOStream(final String fileName) {
		InputStream ioStream = LanguagePack.class.getClassLoader().getResourceAsStream(fileName);
		if (ioStream == null) {
			throw new IllegalArgumentException(fileName + " is not found");
		}
		return ioStream;
	}

	public static String getString(String key) {
		if (stringsMap.containsKey(key)) {
			LanguagePackValue value = stringsMap.get(key);
			return value.getForLang(language);
		} else {
			String mapKeyDefault = key + languageDefault;
			LanguagePackValue defaultString = stringsMap.get(mapKeyDefault);
			if (defaultString == null) {
				throw new NoSuchElementException(
						String.format("value for key [%s] for EN not found inf resurce file LanguagePack.txt", key));
			}
			return defaultString.getForLang(languageDefault);
		}
	}

	public static void main(String[] args) {
		System.err.println(LanguagePack.getString(LanguagePackKeys.FOUNDPATHTOSERVICE));
		System.err.println(LanguagePack.getString(LanguagePackKeys.UPDATEGITREPO));
	}

	private static void readLanguagePackFile() throws IOException {
		try (InputStream fileInputStream = getFileAsIOStream("LanguagePack.txt");
				InputStreamReader isr = new InputStreamReader(fileInputStream);
				BufferedReader br = new BufferedReader(isr)) {

			String line;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				String[] data = line.split("=");
				String key = data[0];
				LanguagePackValue languagePackValue = stringsMap.computeIfAbsent(key, t -> new LanguagePackValue());
				String lang = data[1];
				String value = data[2];
				languagePackValue.setForLang(lang, value);
			}
		}

	}

	private LanguagePack() {
	}
}
