package com.atguigu.order.config;

import feign.Logger;
import feign.RetryableException;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import reactor.util.retry.Retry;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class OrderServiceConfig {

//    @Bean
    Retryer retryer(){
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
