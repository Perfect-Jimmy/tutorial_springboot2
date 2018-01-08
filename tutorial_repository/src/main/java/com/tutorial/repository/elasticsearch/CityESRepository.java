package com.tutorial.repository.elasticsearch;

import com.tutorial.domain.elasticsearch.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Jimmy. 2018/1/8  22:09
 */
@Repository
public interface  CityESRepository extends ElasticsearchRepository<City, Long> {
}
