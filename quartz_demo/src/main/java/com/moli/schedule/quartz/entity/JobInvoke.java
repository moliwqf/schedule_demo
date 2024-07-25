package com.moli.schedule.quartz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author moli
 * @time 2024-07-17 08:59:24
 * @description 任务调用
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobInvoke implements Serializable {

    private String invokeTarget; // 调用字符串

    private String cron; // cron 表达式

    private String jobName; // 任务名

    private String jobGroup; // 任务组

    private Integer concurrent; // 能否并行

    private String remark; // 评论
}
