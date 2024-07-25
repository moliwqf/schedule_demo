package com.moli.schedule.quartz.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.moli.schedule.quartz.entity.JobIdentity;
import com.moli.schedule.quartz.job.AbstractQuartzJob;
import com.moli.schedule.quartz.job.ConcurrentJob;
import com.moli.schedule.quartz.job.DisConcurrentJob;
import com.moli.schedule.quartz.service.IScheduleService;
import com.moli.schedule.quartz.entity.JobInvoke;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author moli
 * @time 2024-07-16 15:54:34
 * @description 定时任务服务
 */
@Slf4j
@Service
public class ScheduleServiceImpl implements IScheduleService {

    private String defaultGroup = "default_group";

    @Autowired
    private Scheduler scheduler;

    @Override
    public String scheduleJob(Class<? extends Job> jobBeanClass, String cron, String data) {
        String jobName = UUID.fastUUID().toString();
        JobDetail jobDetail = JobBuilder.newJob(jobBeanClass)
                .withIdentity(jobName, defaultGroup)
                .usingJobData("data", data)
                .build();
        // 创建触发器，指定任务执行时间
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, defaultGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        // 调度器进行任务调度
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.error("任务调度执行失败{}", e.getMessage());
        }
        return jobName;
    }

    @Override
    public String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String data) {
        // 日期转CRON表达式
        String startCron = String.format("%d %d %d %d %d ? %d",
                DateUtil.second(startTime),
                DateUtil.minute(startTime),
                DateUtil.hour(startTime, true),
                DateUtil.dayOfMonth(startTime),
                DateUtil.month(startTime) + 1,
                DateUtil.year(startTime));
        return scheduleJob(jobBeanClass, startCron, data);
    }

    @Override
    public Boolean cancelScheduleJob(String jobName) {
        boolean success = false;
        try {
            // 暂停触发器
            scheduler.pauseTrigger(new TriggerKey(jobName, defaultGroup));
            // 移除触发器中的任务
            scheduler.unscheduleJob(new TriggerKey(jobName, defaultGroup));
            // 删除任务
            scheduler.deleteJob(new JobKey(jobName, defaultGroup));
            success = true;
        } catch (SchedulerException e) {
            log.error("任务取消失败{}", e.getMessage());
        }
        return success;
    }

    @Override
    public boolean scheduleJob(JobInvoke jobInvoke) {
        Class<? extends Job> jobBeanClass = jobInvoke.getConcurrent() == 1 ? ConcurrentJob.class : DisConcurrentJob.class;
        String jobJson = JSONUtil.toJsonStr(jobInvoke);
        JobDetail jobDetail = JobBuilder.newJob(jobBeanClass)
                .withIdentity(jobInvoke.getJobName(), jobInvoke.getJobGroup())
                .withDescription(jobInvoke.getRemark())
                .usingJobData(AbstractQuartzJob.TASK_DATA_KEY, jobJson)
                .build();
        // 创建触发器，指定任务执行时间
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withDescription(jobInvoke.getRemark())
                .withSchedule(CronScheduleBuilder.cronSchedule(jobInvoke.getCron()))
                .build();
        // 调度器进行任务调度
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
//            scheduler.getListenerManager().addJobListener();
            return true;
        } catch (SchedulerException e) {
            log.error("任务调度执行失败 {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteJob(JobIdentity jobIdentity) {
        try {
            // 暂停触发器
            scheduler.pauseTrigger(new TriggerKey(jobIdentity.getJobName(), jobIdentity.getJobGroup()));
            // 移除触发器中的任务
            scheduler.unscheduleJob(new TriggerKey(jobIdentity.getJobName(), jobIdentity.getJobGroup()));
            // 删除任务
            scheduler.deleteJob(new JobKey(jobIdentity.getJobName(), jobIdentity.getJobGroup()));
        } catch (Exception e) {
            log.error("任务取消失败{}", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean pauseOrStartJob(JobIdentity jobIdentity) {
        boolean needStart = jobIdentity.getStatus() == 1;
        try {
            if (needStart) {
                scheduler.resumeTrigger(new TriggerKey(jobIdentity.getJobName(), jobIdentity.getJobGroup()));
                scheduler.resumeJob(new JobKey(jobIdentity.getJobName(), jobIdentity.getJobGroup()));
            } else {
                scheduler.pauseTrigger(new TriggerKey(jobIdentity.getJobName(), jobIdentity.getJobGroup()));
                scheduler.pauseJob(new JobKey(jobIdentity.getJobName(), jobIdentity.getJobGroup()));
            }
        } catch (Exception e) {
            log.error("IScheduleService.pauseOrStartJob() 方式执行失败, 错误信息：{}", e.getMessage());
            return false;
        }
        return true;
    }
}
