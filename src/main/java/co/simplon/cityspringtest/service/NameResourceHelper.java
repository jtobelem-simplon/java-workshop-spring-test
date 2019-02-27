package co.simplon.cityspringtest.service;

import java.util.Arrays;
import java.util.List;

public class NameResourceHelper {

	private static final String URL_SPLIT = "-";
	private static final String NAME_SPLIT = " ";

	public static String urlToName(String url) {
		String result = "";
		List<String> splitChunks = split(url, URL_SPLIT);

		for (int i = 0; i < splitChunks.size(); i++) {
			String chunk = splitChunks.get(i);
			if (chunk.length() > 0) {
				String newChunk;
				newChunk = chunk.substring(0, 1).toUpperCase();
				
				if (chunk.length() > 1)
					newChunk += chunk.substring(1, chunk.length());
				
				result += (i != splitChunks.size() - 1) ? newChunk + NAME_SPLIT : newChunk;
			}
		}
		return result;
	}

	public static String nameToUrl(String name) {
		String result = "";
		List<String> splitChunks = split(name, NAME_SPLIT);

		for (int i = 0; i < splitChunks.size(); i++) {
			String chunk = splitChunks.get(i);
			if (chunk.length() > 0) {
				chunk = chunk.toLowerCase();
								
				result += (i != splitChunks.size() - 1) ? chunk + URL_SPLIT : chunk;
			}
		}
		return result;
	}

	private static List<String> split(String toSplit, String splitter) {
		return Arrays.asList(toSplit.split(splitter));
	}
}
