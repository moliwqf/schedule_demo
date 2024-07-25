package com.moli.task_logback.timer;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author moli
 * @time 2024-07-16 14:57:33
 * @description Timer 实现定时任务
 */
@Slf4j
public class TimerDemo {

    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                log.debug("当前时间{}线程名称{}", new Date(),
                        Thread.currentThread().getName());
            }
        };
        log.debug("当前时间{}线程名称{}", new Date(),
                Thread.currentThread().getName());
        Timer timer = new Timer("TimerDemo");
        timer.schedule(task, 1000L);
    }
}
