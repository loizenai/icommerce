package com.ozen.icommerce.service.signin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ozen.icommerce.dto.request.login.SignIn;
import com.ozen.icommerce.dto.response.signin.UserDto;
import com.ozen.icommerce.repository.user.RoleRepository;
import com.ozen.icommerce.repository.user.UserRepository;
import com.ozen.icommerce.security.jwt.JwtProvider;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SignInServiceImpl implements SignInService{
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@Override
	public UserDto signin(SignIn signIn) {
		try {
			final var authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signIn.getUsername(), signIn.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			final var jwt = jwtProvider.generateJwtToken(authentication);
			final var userDetails = (UserDetails) authentication.getPrincipal();
			
			return UserDto.builder()
				.token(jwt)
				.username(userDetails.getUsername())
				.authorities(userDetails.getAuthorities())
				.build();
		} catch (Exception e) {
			log.error("Error: {}", e);
			throw e;
		}
	}
}
