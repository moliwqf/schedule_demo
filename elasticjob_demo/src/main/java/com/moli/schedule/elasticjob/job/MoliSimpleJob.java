package com.moli.schedule.elasticjob.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @author moli
 * @time 2024-07-25 16:28:46
 * @description 通过实现 SimpleJob 接口来定义一个 job
 */
@Slf4j
@Component
public class MoliSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        // 分片执行
        switch (shardingContext.getShardingItem()) {
            case 0:
                log.info("分片1：执行任务");
                break;
            case 1:
                log.info("分片2：执行任务");
                break;
            case 2:
                log.info("分片3：执行任务");
                break;
        }
    }
}
