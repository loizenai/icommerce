package com.ozen.icommerce.controller.auth;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ozen.icommerce.dto.request.login.SignIn;
import com.ozen.icommerce.dto.request.login.SignUp;
import com.ozen.icommerce.dto.response.ApiResponseEntity;
import com.ozen.icommerce.dto.response.signin.UserDto;

import io.swagger.v3.oas.annotations.Operation;

public interface AuthOperation {
  @Operation(summary = "Sign-In!", description = "Sign-In!")
  @PostMapping(value="/signin", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponseEntity<UserDto> authenticateUser(@Valid @RequestBody SignIn signIn);
  
  @Operation(summary = "Sign-Up!", description = "Sign-In!")
  @PostMapping(value="/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponseEntity<?> signUp(@Valid @RequestBody SignUp request);
}
