package com.ozen.icommerce.es.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;

import com.ozen.icommerce.dto.request.filter.FilterRequest;
import com.ozen.icommerce.es.entity.EsProductEntity;

public interface EsProductFilterService {
	SearchHits<EsProductEntity> doFilter(FilterRequest request, Pageable page);
}
