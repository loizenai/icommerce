package com.ozen.icommerce.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ozen.icommerce.service.UserDetailsServiceImpl;
import com.ozen.icommerce.token.Session;
import com.ozen.icommerce.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtProvider tokenProvider;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private Session token;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			final var jwt = Utils.getJwt(request);
			
			if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
				final var username = tokenProvider.getUserNameFromJwtToken(jwt);

				final var userDetails = userDetailsService.loadUserByUsername(username);
				final var authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				// set token
				token.setValue(jwt);
			}
		} catch (Exception e) {
			log.error("Can NOT set user authentication -> Message: {}", e);
		}

		filterChain.doFilter(request, response);
	}
}