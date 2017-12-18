package com.tutorial.test.redis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by Jimmy. 2017/12/18  17:24
 * 散列可以将多个键值对存储到一个Redis键里面put(key,hashKey,value)
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class HashRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void saveTest(){
        redisTemplate.opsForHash().put("hash","name","lucy");
        redisTemplate.opsForHash().put("hash","age","17");
        Map<String,String> map = Maps.newHashMap();
        map.put("江苏","无锡");
        map.put("上海","上海");
        redisTemplate.opsForHash().put("hash","zone",map);
        redisTemplate.opsForHash().putAll("hash",map);
    }

    /**
     * 删除指定的hashkey Long delete(H key, Object... hashKeys)
     */
    @Test
    public void deleteTest(){
        redisTemplate.opsForHash().put("hash_delete","name","lucy");
        redisTemplate.opsForHash().put("hash_delete","age","17");
        Map<String,String> map = Maps.newHashMap();
        map.put("江苏","无锡");
        map.put("上海","上海");
        redisTemplate.opsForHash().putAll("hash_delete",map);
        System.out.println(redisTemplate.opsForHash().delete("hash_delete","name","上海"));
        System.out.println(redisTemplate.opsForHash().entries("hash_delete"));
    }

    /**
     * 确定哈希hashKey是否存在 Boolean hasKey(H key, Object hashKey)
     */
    @Test
    public void hasHashKeyTest(){
        redisTemplate.opsForHash().put("hash_key","name","lucy");
        redisTemplate.opsForHash().put("hash_key","age","17");
        Map<String,String> map = Maps.newHashMap();
        map.put("江苏","无锡");
        map.put("上海","上海");
        redisTemplate.opsForHash().putAll("hash_key",map);
        System.out.println(redisTemplate.opsForHash().hasKey("hash_key","age"));
        System.out.println(redisTemplate.opsForHash().hasKey("hash_key","age_1"));
    }

    /**
     * 获取指定hash key的值 HV get(H key, Object hashKey);
     * 获取多个key值 List<HV> multiGet(H key, Collection<HK> hashKeys);
     * 获取所有value值 List<HV> values(H key);
     */
    @Test
    public void getkeyValueTest(){
        redisTemplate.opsForHash().put("hash_value","name","lucy");
        redisTemplate.opsForHash().put("hash_value","age","17");
        Map<String,String> map = Maps.newHashMap();
        map.put("江苏","无锡");
        map.put("上海","上海");
        redisTemplate.opsForHash().putAll("hash_value",map);
        System.out.println(redisTemplate.opsForHash().get("hash_value","name"));

        List<Object> objects = Lists.newArrayList();
        objects.add("name");
        objects.add("age");
        System.out.println(redisTemplate.opsForHash().multiGet("hash_value",objects));

        System.out.println(redisTemplate.opsForHash().values("hash_value"));
    }

    /**
     * 给指定的key增加值
     * Long increment(H key, HK hashKey, long delta);
     * Double increment(H key, HK hashKey, double delta);
     */
    @Test
    public void incrementTest(){
        redisTemplate.opsForHash().put("hash_increment","age",18);
        System.out.println(redisTemplate.opsForHash().increment("hash_increment","age",1));
    }

    /**
     * 获取散列表中的key Set<HK> keys(H key)
     * 获取key的长度 Long size(H key);
     */
    @Test
    public void getKeysTest(){
        redisTemplate.opsForHash().put("hash_keys","name","lucy");
        redisTemplate.opsForHash().put("hash_keys","age","17");
        Map<String,String> map = Maps.newHashMap();
        map.put("江苏","无锡");
        map.put("上海","上海");
        redisTemplate.opsForHash().putAll("hash_keys",map);

        System.out.println(redisTemplate.opsForHash().keys("hash_keys"));
        System.out.println(redisTemplate.opsForHash().size("hash_keys"));
    }

    /**
     * 迭代Cursor<Map.Entry<HK, HV>> scan(H key, ScanOptions options)
     */
    @Test
    public void scanTest(){
        redisTemplate.opsForHash().put("hash_cursor","name","lucy");
        redisTemplate.opsForHash().put("hash_cursor","age","17");
        Map<String,String> map = Maps.newHashMap();
        map.put("江苏","无锡");
        map.put("上海","上海");
        redisTemplate.opsForHash().putAll("hash_scan",map);

        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan("hash_cursor", ScanOptions.NONE);
        while(curosr.hasNext()){
            Map.Entry<Object, Object> entry = curosr.next();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }

}