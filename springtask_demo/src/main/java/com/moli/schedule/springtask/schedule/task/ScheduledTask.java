package com.moli.schedule.springtask.schedule.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @author moli
 * @time 2024-07-25 17:06:31
 * @description 定时任务
 */
public final class ScheduledTask {

    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}

