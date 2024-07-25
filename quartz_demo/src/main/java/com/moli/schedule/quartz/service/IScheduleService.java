package com.moli.schedule.quartz.service;

import com.moli.schedule.quartz.entity.JobIdentity;
import com.moli.schedule.quartz.entity.JobInvoke;
import org.quartz.Job;

import java.util.Date;

/**
 * @author moli
 * @time 2024-07-16 15:53:50
 * @description 定时任务服务
 */
public interface IScheduleService {

    /**
     * 通过 Cron 表达式来调度任务
     */
    String scheduleJob(Class<? extends Job> jobBeanClass, String cron, String data);

    /**
     * 指定时间来调度任务
     */
    String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, Date startTime, String data);

    /**
     * 取消定时任务
     */
    Boolean cancelScheduleJob(String jobName);

    /**
     * 定时任务调度
     * @param jobInvoke @see {com.moli.task_logback.quartz.entity.JobInvoke}
     */
    boolean scheduleJob(JobInvoke jobInvoke);

    /**
     * 定时任务删除
     */
    boolean deleteJob(JobIdentity jobIdentity);

    boolean pauseOrStartJob(JobIdentity jobIdentity);
}
