package com.ozen.icommerce.es.service;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.ozen.icommerce.constant.Constants;
import com.ozen.icommerce.dto.request.filter.FilterRequest;
import com.ozen.icommerce.es.entity.EsProductEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EsProductFilterServiceImpl implements EsProductFilterService{

	@Autowired
    private ElasticsearchOperations operations;
	
	@Override
	public SearchHits<EsProductEntity> doFilter(FilterRequest filter, Pageable page) {
		// ### Starting New Way
	    // 0. NOT - Query
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		
		// 1. >>> PRICE
		RangeQueryBuilder priceQuery = null;
		if (filter.getPriceMin() != null
				&& filter.getPriceMax() != null) {
	    	priceQuery = QueryBuilders.rangeQuery(Constants.PRICE)
	    			.from(filter.getPriceMin())
	    			.to(filter.getPriceMax());
		} else if (filter.getPriceMin() != null) {
	    	priceQuery = QueryBuilders.rangeQuery(Constants.PRICE)
	    			.from(filter.getPriceMin());
		} else if (filter.getPriceMax() != null) {
	    	priceQuery = QueryBuilders.rangeQuery(Constants.PRICE)
	    			.from(filter.getPriceMax());
		}
		
		// 1. >>> Check Price Query be Constructed or NOT
		if (priceQuery != null) {
	    	boolQuery.must(priceQuery);
		}
		
		// 2. BRAND
		if (filter.getBrand() != null && !Constants.EMPTY.equals(filter.getBrand())) {
			final var brandQuery = QueryBuilders.matchQuery(Constants.BRAND
					, filter.getBrand());
			boolQuery.must(brandQuery);
		}
		
		// 3. CATEGORY
		if (filter.getCategory() != null && !Constants.EMPTY.equals(filter.getCategory())) {
			final var categoryQuery = QueryBuilders.matchQuery(Constants.CATEGORY
					, filter.getCategory());
			boolQuery.must(categoryQuery);
		}
		
		// 4. COLORs
		if (filter.getColor() != null && !Constants.EMPTY.equals(filter.getColor())) {
			final var colorsQuery = QueryBuilders.matchQuery("colors.color"
					, filter.getColor());
			boolQuery.must(colorsQuery);
		}
		
		// >>> BUILD Native-Search-Query
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
				.withFilter(boolQuery)
				.withPageable(page);
		
	    // => BUILD Query
	    Query query = nativeSearchQueryBuilder
	    		.withQuery(boolQuery)
	    		.build();
		
	    // => BUILD Query
		return operations.search(query, EsProductEntity.class);
	}
}
