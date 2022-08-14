package com.ozen.icommerce.service.filter;

import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import com.ozen.icommerce.dto.pagination.PageProperties;
import com.ozen.icommerce.dto.request.filter.FilterRequest;
import com.ozen.icommerce.es.service.EsProductFilterService;
import com.ozen.icommerce.service.BaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl extends BaseService implements FilterService {

	private final EsProductFilterService esProductFilterService; 
	
	@Override
	public PageProperties doFilter(FilterRequest filter) {
		// >>> to ES Searching
		final var page = buildPagable(filter.getPage());
		final var hits = esProductFilterService.doFilter(filter, page);
		
		final var result = hits.stream().map(SearchHit::getContent).collect(Collectors.toList());
		
		final var totalPages = hits.getTotalHits() % page.getPageSize() != 0 ? 
									(int)Math.ceil(hits.getTotalHits() / page.getPageSize()) + 1
									: (int)Math.ceil(hits.getTotalHits() / page.getPageSize());
		
		// >>> Build Pagination
		return PageProperties.builder()
				.result(result)
				.currentPage(page.getPageNumber())
				.pageSize(page.getPageSize())
				.currentItems(result.size())
				.totalItems(hits.getTotalHits())
				.totalPages(totalPages)
				.build();
	}
}
