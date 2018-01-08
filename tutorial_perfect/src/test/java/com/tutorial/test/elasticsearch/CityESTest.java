package com.tutorial.test.elasticsearch;

import com.tutorial.App;
import com.tutorial.domain.elasticsearch.City;
import com.tutorial.elasticsearch.service.CityESService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Jimmy. 2018/1/8  22:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class CityESTest {
    @Autowired
    private CityESService cityESService;

    @Test
    public void saveCityTest(){
        City city = new City();
        city.setId(1L);
        city.setName("wuxi");
        cityESService.saveCity(city);
    }
}
