package com.atguigu.order;

import com.atguigu.order.feign.WeatherFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class WeatherTest {

    @Autowired
    WeatherFeignClient weatherFeignClient;

    @Test
    void getWeather() throws JsonProcessingException {
        String weather = weatherFeignClient.getWeather("APPCODE 93b7e19861a24c519a7548b17dc16d75","2182","50b53ff8dd7d9fa320d3d3ca32cf8ed1");
        System.out.println(weather);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(weather, Map.class);
        Map<String, Object> cityMap = (Map<String, Object>)((Map<String, Object>) map.get("data")).get("city");
        System.out.println(map.get("code"));
        System.out.println(map.get("data"));
        System.out.println(cityMap);
    }
}
