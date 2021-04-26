package com.learn.springcloud.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.learn.springcloud.entities.CommonResult;
import com.learn.springcloud.entities.Payment;
import com.learn.springcloud.lb.LoadBalancer;
import com.learn.springcloud.lb.LoadBalancerImpl;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
public class controller {
    //public static final String PaymentSrv_URL = "http://localhost:8001"; //单机版
    public static final String PaymentSrv_URL = "http://CLOUD-PAYMENT-SERVICE"; //微服务集群，写微服务名称；由于服务下集群了2台机器，所有需要是负载均衡
    @Resource
    public RestTemplate restTemplate;



    @Resource
    private LoadBalancer LoadBalancer;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult getOrder(@PathVariable Long id){
        System.out.println("方法进入");
        return  restTemplate.getForObject(PaymentSrv_URL+"/payment/get/"+id,
               CommonResult.class,id);
    }

    @GetMapping("/consumer/payment/get1/{id}")
    public CommonResult getOrder2(@PathVariable Long id){
        ResponseEntity<CommonResult> forEntity =
                restTemplate.getForEntity(PaymentSrv_URL + "/payment/get/" + id, CommonResult.class);
        final HttpStatus statusCode = forEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            return new CommonResult(200,"成功");
        } else {
            return new CommonResult(500,"失败");
        }

    }


    @GetMapping("/consumer/payment/create") //客户端用浏览器是get请求，但是底层实质发送post调用服务端8001
    public CommonResult create(@RequestBody Payment payment){
        return restTemplate.postForObject(PaymentSrv_URL + "/payment/create",payment,CommonResult.class);
    }

    @Resource
    private DiscoveryClient discoveryClient;
    //负载均衡
    @GetMapping("/consumer/payment/lb/{id}")
    public CommonResult myLoadBan(@PathVariable Long id){
        System.out.println("自己写的负载均衡算法");
        final List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        ServiceInstance instances1 = LoadBalancer.instances(instances);
        URI url = instances1.getUri();
        return  restTemplate.getForObject(url+"/payment/get/"+id,
                CommonResult.class,id);
    }

}
