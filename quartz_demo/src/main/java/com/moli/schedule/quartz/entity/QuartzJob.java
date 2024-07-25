package com.moli.schedule.quartz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author moli
 * @time 2024-07-16 17:32:51
 * @description 任务
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuartzJob {

    private Integer id;

    private String jobName;

    private String jobGroup;

    private String invokeTarget;

    private String cron;

    private Integer misfirePolicy;

    private Integer concurrent;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String remark;

    private Date nextValidTime;
}
