package com.ozen.icommerce.service.card;

import java.util.List;

import com.ozen.icommerce.dto.card.CardItem;

public interface CardService {
	void addCard(CardItem cardItem);
	void modifyCard(CardItem cardItem);
	void deleteCard(Long id);
	List<CardItem> loadAllCard();
	void emptyCard();
	CardItem selectCardItem(Long id);
}
