package com.moli.schedule.springtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author moli
 * @time 2024-07-16 14:24:17
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class TaskLogbackDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskLogbackDemoApplication.class, args);
    }
}
