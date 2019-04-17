package co.simplon.cityspringtest.exception;

import java.text.MessageFormat;

public class MonumentNameNotFoundException extends Exception {
	
	private static final String PATTERN = "The monument {0} of the city {1} was not found.";
	
	public MonumentNameNotFoundException(String cityName, String monumentName) {
		super(MessageFormat.format(PATTERN, monumentName, cityName));
	}

}
