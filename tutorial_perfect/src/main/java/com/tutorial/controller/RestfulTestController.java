package com.tutorial.controller;

import com.tutorial.domain.User;
import com.tutorial.service.UserService;
import com.tutorial.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jimmy. 2017/12/28  15:36
 *  @Consumes(MediaType.TEXT_PLAIN)
 @Produces(MediaType.TEXT_PLAIN)
 */
@RestController
@RequestMapping("/rest")
public class RestfulTestController {
    private Logger LOGGER = LoggerFactory.getLogger(RestfulTestController.class);

    @Autowired
    private UserService userService;

    /**
     * @PathVariable
     * 请求URL:http://localhost:8080/rest/1
     * @param id
     * @param name
     * @return
     */
    @GetMapping("/user/{id}/{name}")
    public String queryUser1(@PathVariable("id")Long id,@PathVariable("name")String name){
        LOGGER.info("rest query user by id {},name {}",id,name);
        return Constant.SUCCESS;
    }

    /**
     * @RequestParam
     * 请求URL:http://localhost:8080/rest/user?name=jimmy
     * @param name
     * @return
     */
    @GetMapping("/user")
    public String queryUser2(@RequestParam(value = "name",required = false)String name){
        LOGGER.info("rest query user by name {}",name);
        return Constant.SUCCESS;
    }

    /**
     * @RequestBody
     * 接收json格式数据{"id":1,"userName":"jimmy"}自动转换成bean对象
     * @param user
     * @return
     */
    @PostMapping("/user")
    public String addUser1(@RequestBody User user){
        LOGGER.info("rest add user {}",user);
        return Constant.SUCCESS;
    }

    @PutMapping("/user")
    public String modifyUser(){
        LOGGER.info("rest modify user");
        return Constant.SUCCESS;
    }

    @DeleteMapping("/user")
    public String delUser(){
        LOGGER.info("rest delete user");
        return Constant.SUCCESS;
    }
}
