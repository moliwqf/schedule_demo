package com.moli.schedule.quartz.controller;

import com.moli.schedule.common.ReturnData;
import com.moli.schedule.quartz.entity.JobIdentity;
import com.moli.schedule.quartz.service.IScheduleService;
import com.moli.schedule.quartz.entity.JobInvoke;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author moli
 * @time 2024-07-17 08:55:57
 */
@RestController
public class QuartzController {

    @Resource
    private IScheduleService scheduleService;

    // 定时任务创建
    @PostMapping("scheduleJob")
    public ReturnData<?> scheduleJob(@RequestBody JobInvoke jobInvoke) {
        boolean success = scheduleService.scheduleJob(jobInvoke);
        if (success) {
            return ReturnData.ok();
        }
        return ReturnData.fail();
    }

    // 定时任务删除
    @PostMapping("deleteJob")
    public ReturnData<?> deleteJob(@RequestBody JobIdentity jobIdentity) {
        boolean success = scheduleService.deleteJob(jobIdentity);
        if (success) {
            return ReturnData.ok();
        }
        return ReturnData.fail();
    }

    // 定时任务暂停或者开启
    @PostMapping("pauseOrStartJob")
    public ReturnData<?> pauseOrStartJob(@RequestBody JobIdentity jobIdentity) {
        boolean success = scheduleService.pauseOrStartJob(jobIdentity);
        if (success) {
            return ReturnData.ok();
        }
        return ReturnData.fail();
    }
}
