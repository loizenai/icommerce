package com.ozen.icommerce.controller.card;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ozen.icommerce.dto.card.CardItem;
import com.ozen.icommerce.dto.response.ApiResponseEntity;

import io.swagger.v3.oas.annotations.Operation;

public interface CardOperations {
	  @Operation(summary = "Adding-Card!", description = "Adding-New-Items-To-Card!")
	  @PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_VALUE)
	  ApiResponseEntity<String> addCard(@Valid @RequestBody CardItem item/*, @RequestHeader (name="Authorization") String token*/);

	  @Operation(summary = "Retrieve-Card-By-ID!", description = "Retrieve-Card-By-ID!")
	  @GetMapping(value="/retrieve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	  ApiResponseEntity<CardItem> retrieveCardById(@PathVariable Long id);

	  @Operation(summary = "Retrieve-All-Cards", description = "Retrieve-All-Cards")
	  @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
	  ApiResponseEntity<List<CardItem>> retrieveAllCards();
	  
	  @Operation(summary = "Modify-Card!", description = "Modify-Card!")
	  @PutMapping(value="/modify", produces = MediaType.APPLICATION_JSON_VALUE)
	  ApiResponseEntity<String> modifyCard(@Valid @RequestBody CardItem item);
	  
	  @Operation(summary = "Remove-All-Cards!", description = "Remove-All-Cards!")
	  @DeleteMapping(value="/remove/all", produces = MediaType.APPLICATION_JSON_VALUE)
	  ApiResponseEntity<String> cleanCard();
	  
	  @Operation(summary = "Remove-Card-By-Id!", description = "Remove-Card-By-Id!")
	  @DeleteMapping(value="/remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	  ApiResponseEntity<String> removeCardById(@PathVariable Long id);
}
