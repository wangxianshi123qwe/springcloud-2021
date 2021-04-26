package com.learn.springcloud.lb;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class LoadBalancerImpl implements LoadBalancer {
    private AtomicInteger ato = new AtomicInteger(0);
    //获取请求次数
    private  final  int getAndIncrement(){
        int current;
        int next;
        do {//自旋
            current = this.ato.get();
            next = current >= 2147483647 ? 0 : current + 1;//int的最大值：2147483647
        } while(!this.ato.compareAndSet(current, next));//无锁
        return next;
    }
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int indxe = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(indxe);
    }
}
