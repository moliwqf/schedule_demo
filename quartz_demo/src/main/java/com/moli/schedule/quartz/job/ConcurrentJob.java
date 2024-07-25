package com.moli.schedule.quartz.job;

import com.moli.schedule.quartz.entity.QuartzJob;
import com.moli.schedule.quartz.util.JobInvokeUtil;
import org.quartz.JobExecutionContext;

/**
 * @author moli
 * @time 2024-07-17 08:53:33
 * @description 可以并行的任务
 */
public class ConcurrentJob extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob job) throws Exception {
        JobInvokeUtil.invokeMethod(job.getInvokeTarget());
    }
}
