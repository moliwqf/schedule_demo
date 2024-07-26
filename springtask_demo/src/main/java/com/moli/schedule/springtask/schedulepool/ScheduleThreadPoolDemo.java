package com.moli.schedule.springtask.schedulepool;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author moli
 * @time 2024-07-16 15:03:24
 * @description ScheduleThreadPool 实现定时任务
 */
@Slf4j
public class ScheduleThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                log.debug("当前时间{}线程名称{}", new Date(),
                        Thread.currentThread().getName());
            }
        };
        log.debug("当前时间{}线程名称{}", new Date(),
                Thread.currentThread().getName());
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.scheduleAtFixedRate(task, 1000L, 1000L, TimeUnit.MILLISECONDS);
        Thread.sleep(1000 + 1000 * 4);
        executorService.shutdown();
    }
}
