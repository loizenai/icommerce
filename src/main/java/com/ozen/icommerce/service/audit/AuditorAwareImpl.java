package com.ozen.icommerce.service.audit;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ozen.icommerce.config.admin.AdminProperties;
import com.ozen.icommerce.constant.Constants;
import com.ozen.icommerce.entity.user.UserEntity;
import com.ozen.icommerce.repository.user.UserRepository;

public class AuditorAwareImpl implements AuditorAware<UserEntity> {

	@Autowired
    private UserRepository userRepository;
    
	@Autowired
    private AdminProperties adminProperties;

    @Override
    public Optional<UserEntity> getCurrentAuditor() {
    	if (SecurityContextHolder.getContext().getAuthentication() != null) {
    		String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username != null && !Constants.EMPTY.equals(username)) {
                final var user = userRepository.findByUsername(username);
                if (user.isPresent()) {
                	return Optional.of(user.get());
                }
            }
    	}
        
        final var adminMaybe = userRepository.findById(adminProperties.getId());
        
        if (adminMaybe.isPresent()) {
        	return Optional.of(adminMaybe.get());	
        }
        
        return Optional.empty();
    }
}