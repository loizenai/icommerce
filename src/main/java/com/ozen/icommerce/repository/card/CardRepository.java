package com.ozen.icommerce.repository.card;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ozen.icommerce.entity.card.CardEntity;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
	
  @Query(
	      value = "select * from card where status=1 and session_id=:sessionId",
	      nativeQuery = true)
  List<CardEntity> findBySessionId(String sessionId);
  
  @Query(
	      value = "select * from card where status=1 and session_id=:sessionId and id=:id",
	      nativeQuery = true)
  Optional<CardEntity> findByIdAndSessionId(String sessionId, Long id);
  
  @Query(
	      value = "select * from card where status=1 and session_id=:sessionId and product_id=:productId",
	      nativeQuery = true)
  Optional<CardEntity> findByProductIdAndSessionId(String sessionId, Long productId);
  
  @Query(
	      value = "select * from card where status=1 and session_id=:sessionId",
	      nativeQuery = true)
  List<CardEntity> findAllBySessionId(String sessionId);
}
