package com.ozen.icommerce.service.signin;

import com.ozen.icommerce.dto.request.login.SignIn;
import com.ozen.icommerce.dto.response.signin.UserDto;

public interface SignInService {
	UserDto signin(SignIn form);
}
