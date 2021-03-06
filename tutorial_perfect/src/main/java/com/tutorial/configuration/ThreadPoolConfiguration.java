package com.tutorial.configuration;

import com.tutorial.domain.configuration.ThreadPoolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by jimmy. 2017/12/14  15:07
 */
@Component
public class ThreadPoolConfiguration {
    @Autowired
    private ThreadPoolParam threadPoolParam;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolParam.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolParam.getMaxPoolSize());
        executor.setQueueCapacity(threadPoolParam.getQueueCapacity());
        executor.setKeepAliveSeconds(threadPoolParam.getKeepAliveSeconds());
       // executor.setAllowCoreThreadTimeOut(true);
        executor.setThreadNamePrefix("MyExecutor-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        System.out.println(executor);
        return executor;
    }
}
