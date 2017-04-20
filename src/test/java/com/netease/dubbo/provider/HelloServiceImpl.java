package com.netease.dubbo.provider;

import com.netease.dubbo.service.HelloService;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloServiceImpl implements HelloService {

    AtomicInteger i = new AtomicInteger(0);

    @Override
    public String sayHello(String name) {
        try {
            if (i.getAndIncrement() % 3 == 0) {
                TimeUnit.MILLISECONDS.sleep(3000);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Hello " + name;
    }

}
