package com.ozen.icommerce.service.card;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozen.icommerce.dto.card.CardItem;
import com.ozen.icommerce.entity.card.CardEntity;
import com.ozen.icommerce.enums.Status;
import com.ozen.icommerce.exception.ICommerceErrorCode;
import com.ozen.icommerce.exception.ICommerceException;
import com.ozen.icommerce.repository.card.CardRepository;
import com.ozen.icommerce.token.Session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
	
	private final CardRepository cardRepository;
	private final ModelMapper modelMapper;
	private final Session session;
	
	@Override
	public void addCard(CardItem card) {
		try {
			var existedCardOpt = cardRepository.findByProductIdAndSessionId(session.getValue(), card.getProductId());
			existedCardOpt.ifPresentOrElse(e -> {
				throw new ICommerceException(ICommerceErrorCode.CARD_ADD_ERROR_PRODUCT_ID_EXISTED);
			}, () -> {
				final var cardEntity = modelMapper.map(card, CardEntity.class);
				// >>> Attach Token
				cardEntity.setSessionId(session.getValue());
				// >>> Save to DB
				cardRepository.save(cardEntity);
			});
		} catch (ICommerceException ie) {
			throw ie;
		} catch (Exception e) {
			log.error("Adding Card Error", e);
			throw new ICommerceException(ICommerceErrorCode.CARD_ADD_ERROR);
		}
	}

	@Transactional
	@Override
	public void modifyCard(CardItem card) {
		try {
			if (card.getId() == null) {
				log.error("Not Found Item_ID in Request!");
				throw new ICommerceException(ICommerceErrorCode.CARD_MODIFY_ERROR_NOT_FOUND_ITEM_ID);
			}
			
			var existedCardOpt = cardRepository.findByIdAndSessionId(session.getValue(), card.getId());
			
			existedCardOpt.ifPresentOrElse(c -> {
				BeanUtils.copyProperties(card, c);
				c.setSessionId(session.getValue());
				cardRepository.save(c);
			}, () -> log.warn("Not Found Item with Item_ID = {}", card.getId()));
		} catch (Exception e) {
			log.error("Modify Card Error", e);
			throw new ICommerceException(ICommerceErrorCode.CARD_MODIFY_ERROR);
		}
	}

	@Override
	public void deleteCard(Long id) {
		try {
			// Just Soft-Delete
			var existedCardOpt = cardRepository.findByIdAndSessionId(session.getValue(), id);
			existedCardOpt.ifPresentOrElse(c -> {
				c.setStatus(Status.DELETED);
				cardRepository.save(c);
			}, () -> log.warn("Not Found Item with Item_ID = {}", id));
		} catch (Exception e) {
			log.error("Delete Card Error", e);
			throw new ICommerceException(ICommerceErrorCode.CARD_DELETE_ERROR);
		}
	}

	@Override
	public List<CardItem> loadAllCard() {
		try {
			final var cardEntities = cardRepository.findBySessionId(session.getValue());
			
			if (cardEntities == null || cardEntities.isEmpty()) {
				return Collections.emptyList();
			}
			
			return cardEntities.stream()
						.map(e -> modelMapper.map(e, CardItem.class))
						.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Delete Card Error", e);
			throw e;
		}
	}

	@Transactional
	@Override
	public void emptyCard() {
		try {
			// Just Soft-Delete
			final var cards = cardRepository.findAllBySessionId(session.getValue());
			if (cards != null && !cards.isEmpty()) {
				for(CardEntity c: cards) {
					c.setStatus(Status.DELETED);
				}
				cardRepository.saveAll(cards);
			}
		} catch (Exception e) {
			log.error("Error = {}", e);
		}
	}

	@Override
	public CardItem selectCardItem(Long id) {
		try {
			final var cardOpt = cardRepository.findByIdAndSessionId(session.getValue(), id);
			if (cardOpt.isPresent()) {
				return modelMapper.map(cardOpt.get(), CardItem.class);
			} 
		} catch (Exception e) {
			log.error("Error = {}", e);
			throw e;
		}
		throw new ICommerceException(ICommerceErrorCode.CARD_ITEM_NOT_FOUND);
	}
}
