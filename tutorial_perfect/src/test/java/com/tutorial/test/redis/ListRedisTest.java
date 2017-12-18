package com.tutorial.test.redis;

import com.google.common.collect.Lists;
import com.tutorial.domain.Book;
import com.tutorial.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy. 2017/12/18  17:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ListRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void saveListTest(){
        ListOperations<String,User> operations = redisTemplate.opsForList();
        User user1 = new User();
        user1.setUserName("Jack1");

        User user2 = new User();
        user2.setUserName("鑫仔1");

        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);

        redisTemplate.opsForList().leftPush("人",user1);
        redisTemplate.opsForList().leftPush("人",user2);
    }

    @Test
    public void saveListTest2(){
        List<Object> list = Lists.newArrayList();
        User user = null;
        for(Long i=0L;i<1L;i++){
            user = new User();
            user.setId(i+1);
            user.setUserName("Name"+(i+1));
            list.add(user);
        }
        Book book = null;
        for(Long i=0L;i<1L;i++){
            book = new Book();
            book.setId("id"+(i+1));
            book.setName("book"+(i+1));
            list.add(book);
        }
        redisTemplate.opsForList().leftPush("testList",list);
     /*
     [
  "java.util.ArrayList",
  [
    [
      "com.tutorial.domain.User",
      {
        "id": 1,
        "userName": "Name1",
        "password": null,
        "userDesc": null,
        "inCome": null,
        "address": null,
        "birthDay": null
      }
    ],
    [
      "com.tutorial.domain.Book",
      {
        "id": "id1",
        "name": "book1",
        "price": null,
        "userId": null
      }
    ]
  ]
]
     */
    }

    /**
     * 获取指定范围内的元素
     * 偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素
     */
    @Test
    public void rangeTest(){
       /* for(Long i=1L;i<10L;i++){
            User user = new User();
            user.setId(i);
            user.setUserName("user"+i);
            redisTemplate.opsForList().leftPush("list_range",user);
        }*/
        List<User> userList = redisTemplate.opsForList().range("list_range",0,5);
        List<User> all = redisTemplate.opsForList().range("list_range",0,-1);
        System.out.println(userList);
        System.out.println(all);
    }

    /**
     * 修剪现有列表，使其只包含指定的指定范围的元素，起始和停止都是基于0的索引
     */
    @Test
    public void trimTest(){
      /*  for(Long i=1L;i<10L;i++){
            User user = new User();
            user.setId(i);
            user.setUserName("user"+i);
            redisTemplate.opsForList().leftPush("list_trim",user);
        }*/
        redisTemplate.opsForList().trim("list_trim",1,5);
        System.out.println(redisTemplate.opsForList().range("list_trim",0,-1));
    }

    /**
     * 返回存储在键中的列表的长度。如果键不存在，则将其解释为空列表，并返回0
     * 当key存储的值不是列表时返回错误
     */
    @Test
    public void sizeTest(){
        System.out.println(redisTemplate.opsForList().size("list_trim"));
    }

    /**
     * 批量插入数组
     */
    @Test
    public void saveArrayTest(){
        String[] array = StringUtils.delimitedListToStringArray("1,2,3",",");
        //返回插入数据后的长度
        System.out.println(redisTemplate.opsForList().leftPushAll("list_push_array",array));
    }

    /**
     * 指定位置设置值void set(K key, long index, V value);
     */
    @Test
    public void setValueTest(){
        String[] array = StringUtils.delimitedListToStringArray("1,2,3",",");
        //返回插入数据后的长度
        System.out.println(redisTemplate.opsForList().leftPushAll("list_set_value",array));
        redisTemplate.opsForList().set("list_set_value",1,"hello");
    }

    /**
     * 从存储在键中的列表中删除等于值的元素Long remove(K key, long count, Object value);
     * count> 0：删除等于从头到尾移动的值的元素。
     * count <0：删除等于从尾到头移动的值的元素。
     * count = 0：删除等于value的所有元素。
     */
    @Test
    public void removeTest(){
        String[] array = StringUtils.delimitedListToStringArray("1,2,3,4,4,3,7,9",",");
        redisTemplate.opsForList().leftPushAll("list_remove",array);
        redisTemplate.opsForList().remove("list_remove",2,"3");
    }

    /**
     * 根据下标获取值，下标从0开始 V index(K key, long index);
     */
    @Test
    public void indexTest(){
        String[] array = StringUtils.delimitedListToStringArray("1,2,3,4,4,3,7,9",",");
        redisTemplate.opsForList().leftPushAll("list_index",array);
        System.out.println(redisTemplate.opsForList().index("list_index",4));
    }

    /**
     * 弹出最左边的元素，弹出之后该值在列表中将不复存在 V leftPop(K key);
     */
    @Test
    public void popTest(){
        String[] array = StringUtils.delimitedListToStringArray("1,2,3,4,4,3,7,9",",");
        redisTemplate.opsForList().leftPushAll("list_pop",array);
        System.out.println(redisTemplate.opsForList().leftPop("list_pop"));
    }
}
