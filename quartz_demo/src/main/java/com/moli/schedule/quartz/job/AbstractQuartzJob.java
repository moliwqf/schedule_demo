package com.moli.schedule.quartz.job;

import cn.hutool.json.JSONUtil;
import com.moli.schedule.quartz.entity.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author moli
 * @time 2024-07-16 16:59:41
 * @description 抽象任务
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

    private static final ThreadLocal<Date> DATE_THREAD_LOCAL = new ThreadLocal<>();

    public static final String TASK_DATA_KEY = "task_data_key";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        QuartzJob job = JSONUtil.toBean(context.getMergedJobDataMap().getString(TASK_DATA_KEY), QuartzJob.class);
        try {
            before(context, job);
            doExecute(context, job);
            after(context, job, null);
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e);
            after(context, job, e);
        }
    }

    protected abstract void doExecute(JobExecutionContext context, QuartzJob job) throws Exception;

    /**
     * 执行前
     */
    protected void before(JobExecutionContext context, QuartzJob job) {
        DATE_THREAD_LOCAL.set(new Date());
    }

    /**
     * 执行后
     */
    protected void after(JobExecutionContext context, QuartzJob sysJob, Exception e) {
        // 1. 获取起始时间
        Date startTime = DATE_THREAD_LOCAL.get();
        Date now = new Date();
        long runMs = now.getTime() - startTime.getTime();
        /*String jobMessage = job.getJobName() + " 总共耗时：" + runMs + "毫秒";
        THREAD_LOCAL.remove();
        JobLog newJobLog = JobLog.builder()
                .jobId(job.getId())
                .jobName(job.getJobName())
                .jobGroup(job.getJobGroup())
                .invokeTarget(job.getInvokeTarget())
                .startTime(startTime)
                .endTime(now)
                .jobMessage(jobMessage).build();
        if (Objects.nonNull(e)) {
            // 出现异常信息
            newJobLog.setStatus(ZERO);
            newJobLog.setExceptionInfo(ExceptionUtil.getTrace(e));
        } else {
            newJobLog.setStatus(ONE);
        }
        // 添加日志信息
        SpringUtil.getBean(JobLogMapper.class).insert(newJobLog);*/
    }

}
