package com.moli.schedule.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author moli
 * @time 2024-08-01 14:38:27
 * @description 响应码
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(200, "success"),

    FAIL(500, "failed"),

    HTTP_STATUS_200(200, "ok"),
    HTTP_STATUS_400(400, "request error"),
    HTTP_STATUS_401(401, "no authentication"),
    HTTP_STATUS_403(403, "no authorities"),
    HTTP_STATUS_500(500, "server error");

    /**
     * response code
     */
    private final Integer code;

    /**
     * message
     */
    private final String msg;
}
