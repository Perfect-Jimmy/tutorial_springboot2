package com.tutorial.test.jpa;

import com.tutorial.App;
import com.tutorial.domain.User;
import com.tutorial.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by jimmy.
 * 2017/12/14  14:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void saveUserTest(){
        User user = new User();
        user.setUserName("Jimmy");
        user.setPassword("123");
        user.setInCome(100L);
        user.setUserDesc("描述");
        user.setAddress("wuxi");
        user.setBirthDay(new Date());
        userService.saveOrUpdate(user);
    }
}
