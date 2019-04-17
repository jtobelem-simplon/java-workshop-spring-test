package co.simplon.cityspringtest.exception;

import java.text.MessageFormat;

public class CityNameNotFoundException extends Exception {
	
	private static final String PATTERN = "The city {0} was not found.";
	
	public CityNameNotFoundException(String name) {
		super(MessageFormat.format(PATTERN, name));
	}

}
