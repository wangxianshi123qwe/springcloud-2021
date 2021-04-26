package com.learn.springcloud.server;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 超时访问，演示降级
     * 一旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法
     * fallbackMethod表示paymentInfo_TimeOut方法出事了，paymentInfo_TimeOutHander来兜底
     *  我们能接受3秒钟，它运行5秒钟，超时异常。
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
           // @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000"),//线程运行只能接受3秒
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启熔断记载
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "20000"),//多秒中
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率为60%
    })//表示：请求超过3秒就发生降级，在10秒内请求为10次，失败率为60%的就发起熔断
    public String paymentInfo_TimeOut(Integer id){
        /*try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }*/
        if(id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentInfo_TimeOutHandler(Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }






    /* 正常访问，一切OK
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池:"+Thread.currentThread().getName()+"paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O";
    }
}


