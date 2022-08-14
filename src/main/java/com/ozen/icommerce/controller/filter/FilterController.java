package com.ozen.icommerce.controller.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozen.icommerce.dto.pagination.PageProperties;
import com.ozen.icommerce.dto.request.filter.FilterRequest;
import com.ozen.icommerce.dto.response.ApiResponseEntity;
import com.ozen.icommerce.service.filter.FilterService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/filter")
public class FilterController implements FilterOperations {

	@Autowired
	FilterService filterService;
	
	@Timed
	@Counted	
	@Override
	public ApiResponseEntity<PageProperties> filter(FilterRequest filter) {
		return ApiResponseEntity.success(filterService.doFilter(filter));
	}
}
