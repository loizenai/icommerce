package com.ozen.icommerce.es.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ozen.icommerce.es.entity.EsProductEntity;

public interface EsProductRepository  extends ElasticsearchRepository<EsProductEntity, Long> {
  EsProductEntity findByReferId(Long referid);
}