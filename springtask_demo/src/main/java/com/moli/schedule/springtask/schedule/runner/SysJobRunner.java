package com.moli.schedule.springtask.schedule.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moli.schedule.springtask.schedule.entity.SysJob;
import com.moli.schedule.springtask.schedule.enums.IsDeleteEnum;
import com.moli.schedule.springtask.schedule.enums.SysJobStatusEnum;
import com.moli.schedule.springtask.schedule.service.SysJobService;
import com.moli.schedule.springtask.schedule.task.CronTaskRegistrar;
import com.moli.schedule.springtask.schedule.task.SchedulingRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author moli
 * @time 2024-07-26 09:57:39
 * @description 应用启动运行任务
 */
@Slf4j
@Component
public class SysJobRunner implements CommandLineRunner {

    @Resource
    private SysJobService sysJobService;

    @Resource
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) throws Exception {
        // 应用启动加载任务
        log.info("加载定时任务中...");
        List<SysJob> sysJobList = sysJobService.list(
                new LambdaQueryWrapper<SysJob>()
                        .eq(SysJob::getStatus, SysJobStatusEnum.RUNNABLE.getStatus())
                        .eq(SysJob::getIsDelete, IsDeleteEnum.NORMAL.getStatus())
        );
        if (!CollectionUtils.isEmpty(sysJobList)) {
            sysJobList.forEach(job -> {
                SchedulingRunnable runnable = new SchedulingRunnable(
                        job.getBeanName(),
                        job.getMethodName(),
                        job.getMethodParams()
                );
                cronTaskRegistrar.addCronTask(runnable, job.getCron());
            });
        }
        log.info("加载定时任务完毕.");
    }
}
