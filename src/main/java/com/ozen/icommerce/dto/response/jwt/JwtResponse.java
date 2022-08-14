package com.ozen.icommerce.dto.response.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.ozen.icommerce.constant.Constants;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = Constants.BEARER;
	private String username;
	private Collection<? extends GrantedAuthority> authorities;
}