package com.atguigu.product;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@SpringBootTest
public class DiscoveryTest {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    NacosServiceDiscovery nacosServiceDiscovery;

    @Test
    void nacosDiscoveryClientTest() throws NacosException {
        for(String service : nacosServiceDiscovery.getServices()){
            System.out.println("service = " + service);
            List<ServiceInstance> instance = nacosServiceDiscovery.getInstances(service);
            for(ServiceInstance serviceInstance : instance){
                System.out.println("ip: "+serviceInstance.getHost()+" port: "+serviceInstance.getPort());
            }
        }
    }

    @Test
    void discoveryClientTest(){
        for(String service : discoveryClient.getServices()){
            System.out.println("service = " + service);
            List<ServiceInstance> instance = discoveryClient.getInstances(service);
            for(ServiceInstance serviceInstance : instance){
                System.out.println("ip: "+serviceInstance.getHost()+" port: "+serviceInstance.getPort());
            }
        }
    }
}
