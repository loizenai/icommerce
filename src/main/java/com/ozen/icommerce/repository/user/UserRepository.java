package com.ozen.icommerce.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ozen.icommerce.entity.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	@Transactional(propagation = Propagation.REQUIRES_NEW)
    Optional<UserEntity> findByUsername(String username);
	
    Boolean existsByUsername(String username);
    
    Boolean existsByEmail(String email);
}