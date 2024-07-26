package com.moli.schedule.springtask.schedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author moli
 * @time 2024-07-26 09:26:00
 */
@Getter
@AllArgsConstructor
public enum IsDeleteEnum {

    NORMAL(0, "正常"),

    DELETED(1, "删除");

    private final Integer status;

    private final String desc;
}
