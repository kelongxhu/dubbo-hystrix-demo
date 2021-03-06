package com.netease.dubbo.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.netease.dubbo.service.HelloService;

public class Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        context.start();
        final HelloService service = context.getBean("helloService", HelloService.class);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 12; i++) {
            executorService.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(service.sayHello("杨少凯"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });

        }
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //达到10s后，超过x次请求失败，且到达50%,开启熔断状态，则以下请求均失败
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(service.sayHello("杨少凯x"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });
        }


        context.close();

    }

}
