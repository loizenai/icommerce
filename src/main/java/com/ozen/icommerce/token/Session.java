package com.ozen.icommerce.token;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Component
@RequestScope
@Getter
@Setter
public class Session {
	String value;
}
