package com.tutorial.elasticsearch.service.impl;

import com.tutorial.domain.elasticsearch.City;
import com.tutorial.elasticsearch.service.CityESService;
import com.tutorial.repository.elasticsearch.CityESRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jimmy. 2018/1/8  22:15
 */
@Service
public class CityESServiceImpl implements CityESService{
    @Autowired
    private CityESRepository cityESRepository;

    @Override
    public void saveCity(City city) {
        cityESRepository.save(city);
    }
}
