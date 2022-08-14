package com.ozen.icommerce.security.jwt;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ozen.icommerce.config.SecurityConfig;
import com.ozen.icommerce.service.UserPrinciple;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

	private final SecurityConfig securityConfig;
	
    public String generateJwtToken(Authentication authentication) {

        final var userPrincipal = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
		                .setSubject((userPrincipal.getUsername()))
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + Integer.valueOf(securityConfig.getJwtExpiration())*1000))
		                .signWith(SignatureAlgorithm.HS512, securityConfig.getJwtSecret())
		                .compact();
    }
    
    public String generateJwtToken(String userName) {

        return Jwts.builder()
		                .setSubject(userName)
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + Integer.valueOf(securityConfig.getJwtExpiration())*1000))
		                .signWith(SignatureAlgorithm.HS512, securityConfig.getJwtSecret())
		                .compact();
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(securityConfig.getJwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e);
        }
        
        return false;
    }
    
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
			                .setSigningKey(securityConfig.getJwtSecret())
			                .parseClaimsJws(token)
			                .getBody().getSubject();
    }
}