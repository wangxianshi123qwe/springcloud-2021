package com.learn.springcloud.server;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "服务调用失败，提示来自：paymentInfo_OK=====cloud-consumer-feign-order80";
    }
    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "服务调用失败，提示来自：paymentInfo_TimeOut=====cloud-consumer-feign-order80";
    }
}
