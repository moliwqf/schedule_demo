package com.moli.schedule.quartz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author moli
 * @time 2024-07-17 11:38:04
 * @description 定时任务唯一标识
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobIdentity {

    private String jobName; // 任务名

    private String jobGroup; // 任务组

    private Integer status; // 定时任务状态位 1 - start 0 - pause
}
