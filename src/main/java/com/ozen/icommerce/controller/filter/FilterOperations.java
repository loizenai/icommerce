package com.ozen.icommerce.controller.filter;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ozen.icommerce.dto.pagination.PageProperties;
import com.ozen.icommerce.dto.request.filter.FilterRequest;
import com.ozen.icommerce.dto.response.ApiResponseEntity;

import io.swagger.v3.oas.annotations.Operation;

public interface FilterOperations {

  @Operation(summary = "Filter-Data!", description = "Filter-Data!")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ApiResponseEntity<PageProperties> filter(@Valid @RequestBody FilterRequest filter);
}
