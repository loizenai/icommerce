package com.ozen.icommerce.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozen.icommerce.dto.request.login.SignIn;
import com.ozen.icommerce.dto.request.login.SignUp;
import com.ozen.icommerce.dto.response.ApiResponseEntity;
import com.ozen.icommerce.dto.response.signin.UserDto;
import com.ozen.icommerce.service.signin.SignInService;
import com.ozen.icommerce.service.signup.SignUpService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs implements AuthOperation{

	@Autowired
	SignInService signInService;
	
	@Autowired
	SignUpService signUpService;

	@Override
	public ApiResponseEntity<UserDto> authenticateUser(@Valid @RequestBody SignIn signIn) {
		return ApiResponseEntity.success(signInService.signin(signIn));
	}
	
	@Override
	public ApiResponseEntity<?> signUp(@Valid @RequestBody SignUp request) {
		signUpService.signUp(request);
		return ApiResponseEntity.success();
	}
}
