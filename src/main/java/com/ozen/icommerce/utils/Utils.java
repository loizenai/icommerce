package com.ozen.icommerce.utils;

import javax.servlet.http.HttpServletRequest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
	
	public static String getJwt(HttpServletRequest request) {
		final var authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.replace("Bearer ", "");
		}

		return null;
	}
	
	public static String getJwt(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			return token.replace("Bearer ", "");
		}

		return null;
	}
}
