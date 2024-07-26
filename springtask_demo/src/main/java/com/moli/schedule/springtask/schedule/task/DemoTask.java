package com.moli.schedule.springtask.schedule.task;

import org.springframework.stereotype.Component;

/**
 * @author moli
 * @time 2024-07-25 17:13:39
 */
@Component("demoTask")
public class DemoTask {

    public void taskWithParams(String params) {
        System.out.println("执行有参示例任务：" + params);
    }

    public void taskNoParams() {
        System.out.println("执行无参示例任务");
    }
}

