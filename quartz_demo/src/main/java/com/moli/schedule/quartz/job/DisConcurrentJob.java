package com.moli.schedule.quartz.job;

import com.moli.schedule.quartz.entity.QuartzJob;
import com.moli.schedule.quartz.util.JobInvokeUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * @author moli
 * @time 2024-07-17 08:54:38
 * @description 不能并行的任务
 */
@DisallowConcurrentExecution
public class DisConcurrentJob extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, QuartzJob job) throws Exception {
        JobInvokeUtil.invokeMethod(job.getInvokeTarget());
    }
}
