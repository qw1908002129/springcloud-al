package com.atguigu.order.service.impl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.order.bean.Order;
import com.atguigu.order.feign.ProductFeignClient;
import com.atguigu.order.service.OrderService;
import com.atguigu.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadbalancerClient;

    @Autowired
    ProductFeignClient productFeignClient;


    @SentinelResource(value = "createOrder")
    @Override
    public Order createOrder(Long productId, Long userId) {
//        Product product = getProductFromRemoteWithBalanceAnnotation(productId);
        Product product = productFeignClient.getProductById(productId);
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("张三");
        order.setAddress("尚硅谷");
        order.setProductList(Arrays.asList(product));
        return order;
    }

    public Order createOrderFallback(Long productId, Long userId, BlockException  e){
        Order order = new Order();
        order.setId(0L);
        order.setTotalAmount(new BigDecimal("0"));
        order.setUserId(userId);
        order.setNickName("未知用户");
        order.setAddress("异常信息"+e.getClass());
        return order;
    }

    private Product getProductFromRemoteWithBalanceAnnotation(Long productId){
        String url = "http://service-product/product/"+productId;
        log.info("url:"+url);
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    private Product getProductFromRemoteWithBalancer(Long productId){
        ServiceInstance instance=loadbalancerClient.choose("service-product");
        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/product/"+productId;
        log.info("url:"+url);
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    private Product getProductFromRemote(Long productId){
        List< ServiceInstance> instances=discoveryClient.getInstances("service-product");
        ServiceInstance instance=instances.get(0);
        String url = "http://"+instance.getHost()+":"+instance.getPort()+"/product/"+productId;
        log.info("url:"+url);
        Product product = restTemplate.getForObject(url,Product.class);
        return product;
    }

}
