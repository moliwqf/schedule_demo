package com.moli.schedule.quartz;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author moli
 * @time 2024-07-16 22:06:37
 * @description 运行任务类
 */
@Slf4j
@Component("quartzRunner")
public class QuartzRunner {

    public void doLog() throws InterruptedException {
        log.info(Thread.currentThread().getName() + " - schedule task is running - {}", DateUtil.now());
        Thread.sleep(1000 * 3);
        log.info(Thread.currentThread().getName() + " - schedule task is end - {}", DateUtil.now());
    }
}
