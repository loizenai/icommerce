package com.ozen.icommerce.service.filter;

import com.ozen.icommerce.dto.pagination.PageProperties;
import com.ozen.icommerce.dto.request.filter.FilterRequest;

public interface FilterService {
	PageProperties doFilter(FilterRequest filter);
}
