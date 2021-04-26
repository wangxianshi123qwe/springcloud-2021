package com.learn.springcloud;


import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class OrderFeignMain80 {
   /* @Bean
    public RandomRule randomRule(){
        return new RandomRule();
    }*/
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain80.class);
    }
}
