package com.ozen.icommerce.controller.card;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozen.icommerce.constant.ConstantCard;
import com.ozen.icommerce.dto.card.CardItem;
import com.ozen.icommerce.dto.response.ApiResponseEntity;
import com.ozen.icommerce.service.card.CardService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/card")
public class CardController implements CardOperations {
	
	@Autowired
	private CardService cardService;
	
	@Timed
    @Counted
	@Override
	public ApiResponseEntity<String> addCard(@Valid @RequestBody CardItem item) {
		cardService.addCard(item);
		return ApiResponseEntity.success(ConstantCard.ADDING_CARD_SUCCESS);
	}
	
	@Timed
	@Counted
	@Override
	public ApiResponseEntity<String> modifyCard(@Valid CardItem item) {
		cardService.modifyCard(item);
		return ApiResponseEntity.success(ConstantCard.MODIFY_CARD_SUCCESS);
	}

	@Timed
	@Counted
	@Override
	public ApiResponseEntity<List<CardItem>> retrieveAllCards() {
		return ApiResponseEntity.success(cardService.loadAllCard());
	}

	@Timed
	@Counted
	@Override
	public ApiResponseEntity<String> cleanCard() {
		cardService.emptyCard();
		return ApiResponseEntity.success(ConstantCard.CLEAN_ALL_CARD_SUCCESS);
	}
	
	@Timed
	@Counted
	@Override
	public ApiResponseEntity<String> removeCardById(Long id) {
		cardService.deleteCard(id);
		return ApiResponseEntity.success(ConstantCard.DELETE_CARD_SUCCESS);
	}

	@Timed
	@Counted
	@Override
	public ApiResponseEntity<CardItem> retrieveCardById(Long id) {
		return ApiResponseEntity.success(cardService.selectCardItem(id));
	}
}
