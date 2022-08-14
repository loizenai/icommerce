package com.ozen.icommerce.utils;

import com.google.gson.Gson;
import com.ozen.icommerce.constant.Constants;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class JsonFormatted {
	
	public static String jsonFormat(Object obj ) {
		if (obj == null) {
			return Constants.EMPTY;
		}
		
		try {
			return (new Gson().toJson(obj));	
		}catch (Exception e) {
			log.error("Error : {}", e);
			return Constants.EMPTY;
		}
	}
}
