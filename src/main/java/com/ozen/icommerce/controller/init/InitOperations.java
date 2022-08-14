package com.ozen.icommerce.controller.init;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;

public interface InitOperations {
	
  @Operation(summary = "Initial-Data!", description = "Initial-Data!")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  void initialData();
}
