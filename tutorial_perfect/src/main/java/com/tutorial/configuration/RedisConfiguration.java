package com.tutorial.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.domain.configuration.RedisPoolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by Jimmy. 2017/12/18  16:03
 * redis
 * http://www.cnblogs.com/skyessay/p/6485187.html
 * http://www.jianshu.com/p/7bf5dc61ca06
 */
//@Configuration
public class RedisConfiguration {
    @Value("${redis.pool.db1}")
    private int redisDB1;
    @Value("${redis.pool.db2}")
    private int redisDB2;
    @Autowired
    private RedisPoolParam redisPoolParam;


    /**
     * 第一个数据库
     * @return
     */
    @Primary
    @Bean
    public JedisConnectionFactory redisStringConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = getJedisConnectionFactory();
        jedisConnectionFactory.setDatabase(redisDB1);
        return jedisConnectionFactory;
    }
    /**
     * StringRedisTemplate继承自RedisTemplate，只能操作键值都是String类型的数据
     * @param cf
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(@Qualifier("redisStringConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(cf);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new RedisObjectSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }


    /**
     * 第二个数据库
     * @return
     */
    @Bean
    public JedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = getJedisConnectionFactory();
        jedisConnectionFactory.setDatabase(redisDB2);
        return jedisConnectionFactory;
    }


    /**
     * 对象转成json格式
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String,String> redisTemplate(@Qualifier("redisConnectionFactory")RedisConnectionFactory factory){
        RedisTemplate<String,String> redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //  redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //  redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    //公共方法
    public JedisConnectionFactory getJedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisPoolParam.getHost());
        jedisConnectionFactory.setPort(redisPoolParam.getPort());
      /*  JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(redisPoolParam.getMinIdle());
        jedisPoolConfig.setMaxIdle(redisPoolParam.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisPoolParam.getTimeOut());
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setMaxTotal(redisPoolParam.getMaxActive());
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);*/
        return  jedisConnectionFactory;
    }


}
