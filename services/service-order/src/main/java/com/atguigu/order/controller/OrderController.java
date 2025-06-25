package com.atguigu.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.order.bean.Order;
import com.atguigu.order.properties.OrderProperties;
import com.atguigu.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderProperties  orderProperties;

    @GetMapping("/config")
    public String config() {
        return "timeout:"+orderProperties.getTimeout()+",   auto-confirm:"+orderProperties.getAutoConfirm()+",  dburl:"+orderProperties.getDburl();
    }

    @GetMapping("/create")
    public Order createOrder(Long productId,Long userId) {
        Order order = orderService.createOrder(productId,userId);
        return order;
    }

    @GetMapping("/secKill")
    @SentinelResource(value = "secKill-order", fallback = "secKillFallback")
    public Order secKill(@RequestParam(value = "productId", defaultValue = "1000") Long productId,@RequestParam(value = "userId", required = false) Long userId) {
        Order order = orderService.createOrder(productId,userId);
        order.setId(Long.MAX_VALUE);
        return order;
    }

    public Order secKillFallback(Long productId,Long userId, Throwable e) {
        Order order = new Order();
        order.setId(productId);
        order.setUserId(userId);
        order.setAddress("异常信息："+e.getClass());
        return order;
    }

    @GetMapping("/readDB")
    public String readDB() {
        log.info("readDB......");
        return "readDB SUCCESS......";
    }

    @GetMapping("/writeDB")
    public String writeDB() {
        return "writeDB SUCCESS......";
    }

}
