package com.ozen.icommerce.service.signup;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozen.icommerce.dto.request.login.SignUp;
import com.ozen.icommerce.entity.user.RoleEntity;
import com.ozen.icommerce.entity.user.UserEntity;
import com.ozen.icommerce.enums.RoleName;
import com.ozen.icommerce.exception.ICommerceErrorCode;
import com.ozen.icommerce.exception.ICommerceException;
import com.ozen.icommerce.repository.user.RoleRepository;
import com.ozen.icommerce.repository.user.UserRepository;
import com.ozen.icommerce.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class SignUpServiceImpl implements SignUpService{
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
	
	@Autowired
	ModelMapper modelMapper;

	@Transactional
	@Override
	public void signUp(SignUp signUp) {
		try {
			if (Boolean.TRUE.equals(userRepository.existsByUsername(signUp.getUsername().strip()))) {
				throw new ICommerceException(ICommerceErrorCode.SIGN_UP_CONFLICT_USER_EXISTED);
			}

			if (Boolean.TRUE.equals(userRepository.existsByEmail(signUp.getEmail().strip()))) {
				throw new ICommerceException(ICommerceErrorCode.SIGN_UP_CONFLICT_EMAIL_EXISTED);
			}

			// Creating user's account
			final var user = modelMapper.map(signUp, UserEntity.class);
			user.setPassword(encoder.encode(signUp.getPassword()));
			
			Set<RoleEntity> roles = new HashSet<>(); 
			if(signUp.getRole() != null) {
				final var strRoles = signUp.getRole();

				strRoles.forEach(role -> {
					var roleName = RoleName.of(role);
					
					// >>> Check NULL
					if (roleName != null) {
						switch (roleName) {
							case ADMIN:
								final var adminRole = roleRepository.findByName(RoleName.ADMIN)
										.orElseThrow(
												() -> new ICommerceException(ICommerceErrorCode.SIGN_UP_NOT_FOUND_USER_ROLE));
								roles.add(adminRole);
								break;
							case PM:
								final var pmRole = roleRepository.findByName(RoleName.PM)
										.orElseThrow(() -> new ICommerceException(ICommerceErrorCode.SIGN_UP_NOT_FOUND_USER_ROLE));
								roles.add(pmRole);
		
								break;
							default:
								RoleEntity userRole = roleRepository.findByName(RoleName.USER)
										.orElseThrow(() -> new ICommerceException(ICommerceErrorCode.SIGN_UP_NOT_FOUND_USER_ROLE));
								roles.add(userRole);
					}
					}
				});
			} else {
				// default mode : User register 
				final var userRole = roleRepository.findByName(RoleName.USER)
						.orElseThrow(() -> new ICommerceException(ICommerceErrorCode.SIGN_UP_NOT_FOUND_USER_ROLE));
				roles.add(userRole);			
			}
			
			user.setRoles(roles);
			userRepository.save(user);			
		} catch (Exception e) {
			log.error("Error: {}", e);
			throw e;
		}
	}
}
