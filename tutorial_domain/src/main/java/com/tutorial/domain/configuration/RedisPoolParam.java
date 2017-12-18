package com.tutorial.domain.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Jimmy. 2017/12/18  16:11
 */
@Data
@Component
@ConfigurationProperties(prefix="redis.pool")
public class RedisPoolParam {
    private String host;
    private int port;
    private Long timeOut;
    private int maxActive;
    private int minIdle;
    private int maxIdle;

}
