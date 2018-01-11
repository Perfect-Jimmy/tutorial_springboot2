package com.tutorial.elasticsearch.service.impl;

import com.tutorial.domain.elasticsearch.City;
import com.tutorial.elasticsearch.service.CityESService;
import io.searchbox.client.JestClient;
import io.searchbox.indices.CreateIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Jimmy. 2018/1/8  22:15
 */
@Service
public class CityESServiceImpl implements CityESService{
   // @Autowired
  //  private CityESRepository cityESRepository;
   @Autowired
   private JestClient jestClient;
    @Override
    public void saveCity(City city) {
        //cityESRepository.save(city);
        try {
            jestClient.execute(new CreateIndex.Builder("city").build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
