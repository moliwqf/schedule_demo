package com.moli.schedule.springtask.springtask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author moli
 * @time 2024-07-16 15:08:30
 * @description 定时任务类
 */
@Slf4j
@Component
public class CronTask {

    /**
     * cron 表达式版本
     */
    @Scheduled(cron = "0/1 * * ? * ?")
    public void cron() {
        log.info("定时执行, 当前线程: {},时间: {}", Thread.currentThread().getName(), new Date());
    }

    /**
     * fixedRate：固定速率执行。每 5 秒执行一次。
     */
    @Scheduled(fixedRate = 5000)
    public void reportCurrentTimeWithFixedRate() {
        log.info("Current Thread : {}", Thread.currentThread().getName());
        log.info("Fixed Rate Task : The time is now {}", new Date());
    }

    /**
     * fixedDelay：固定延迟执行。距离上一次调用成功后2秒才执行
     */
    @Scheduled(fixedDelay = 2000)
    public void reportCurrentTimeWithFixedDelay() {
        try {
            TimeUnit.SECONDS.sleep(3);
            log.info("Fixed Delay Task : The time is now {}", new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * initialDelay:初始延迟。任务的第一次执行将延迟5秒，然后将以5秒的固定间隔执行。
     */
    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void reportCurrentTimeWithInitialDelay() {
        log.info("Fixed Rate Task with Initial Delay : The time is now {}", new Date());
    }
}
