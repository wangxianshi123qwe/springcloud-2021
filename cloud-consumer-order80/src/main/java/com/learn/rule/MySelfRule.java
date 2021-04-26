package com.learn.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//自定义规则
@Configuration
public class MySelfRule {
    @Bean
    public IRule getRule(){
        return  new RandomRule();
    }
}
