package com.tutorial.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by jimmy on 2017/11/8.
 */
@Data
@Component
@ConfigurationProperties(prefix="spring.task.pool")
public class ThreadPoolParam {
    private int corePoolSize;
    private int maxPoolSize;
    private int keepAliveSeconds;
    private int queueCapacity;
}
