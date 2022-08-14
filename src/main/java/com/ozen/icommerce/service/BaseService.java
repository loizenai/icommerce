package com.ozen.icommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ozen.icommerce.config.pagination.PaginationProperties;
import com.ozen.icommerce.dto.pagination.Pagination;

public abstract class BaseService {
	@Autowired
	PaginationProperties paginationProperties;
	
	protected Pageable buildPagable(Pagination pagination) {
		var page = paginationProperties.getNumber();
		var size = paginationProperties.getSize();

		if (pagination != null) {
			final var requestPage = pagination.getPage();
			if (requestPage != null && requestPage > 0) {
				page = requestPage;
			}

			final var requestSize = pagination.getSize();
			if (requestSize != null && requestSize > 0) {
				size = requestSize;
			}
		}
		
		return PageRequest.of(page, size);
	}
}