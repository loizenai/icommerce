package com.ozen.icommerce.dto.pagination;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageProperties{
	private Object result;
	private long currentPage;
	private long pageSize;
	private long currentItems;
	private long totalItems;
	private int totalPages;
}
