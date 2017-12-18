package com.tutorial.domain.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Jimmy. 2017/12/18  15:31
 * 数据库连接池配置
 */
@Data
@Component
@ConfigurationProperties(prefix="data.source.pool")
public class DataSourcePoolParam {
    private Long connectionTimeout;
    private Long idleTimeout;
    private Long initialSize;
    private Long maxActive;
    private Long minIdle;
    private Long maxIdle;
    private Long maxWait;
    private Long timeBetweenEvictionRunsMillis;
    private Boolean testOnBorrow;
    private Boolean testWhileIdle;
    private String validationQuery;

}
