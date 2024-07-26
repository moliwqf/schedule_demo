package com.moli.schedule.springtask.schedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author moli
 * @time 2024-07-26 09:10:30
 * @description 任务状态
 */
@Getter
@AllArgsConstructor
public enum SysJobStatusEnum {

    RUNNABLE(1, "运行"),

    PAUSE(0, "暂停");

    private final Integer status;

    private final String desc;
}
