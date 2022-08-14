package com.ozen.icommerce.enums;

import java.util.stream.Stream;

public enum  Color {
    RED("RED"),
    BLUE("BLUE"),
    WHITE("WHITE");
    
	private final String value;

	Color(final String value) {
		this.value = value;
	}

	public String value() {
	    return value;
	}
	
	public static Color of(String value) {
		if (value == null) {
			return null;
		}
		return Stream.of(Color.values())
					.filter(e -> e.value().equals(value))
					.findAny().orElse(null);
	}
}
