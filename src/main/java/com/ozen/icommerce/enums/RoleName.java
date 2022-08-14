package com.ozen.icommerce.enums;

import java.util.stream.Stream;

public enum  RoleName {
    USER("USER"),
    PM("PM"),
    ADMIN("ADMIN");
    
	private final String value;

	RoleName(final String value) {
		this.value = value;
	}

	public String value() {
	    return value;
	}
	
	public static RoleName of(String value) {
		if (value == null) {
			return null;
		}
		return Stream.of(RoleName.values())
					.filter(e -> e.value().equals(value))
					.findAny().orElse(null);
	}
}
