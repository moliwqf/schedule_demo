package com.moli.schedule.springtask.schedule.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moli.schedule.springtask.schedule.entity.SysJob;
import com.moli.schedule.springtask.schedule.entity.dto.SysJobSaveOrUpdateDTO;
import com.moli.schedule.springtask.schedule.entity.vo.SysJobVO;
import com.moli.schedule.springtask.schedule.enums.SysJobStatusEnum;
import com.moli.schedule.springtask.schedule.mapper.SysJobMapper;
import com.moli.schedule.springtask.schedule.service.SysJobService;
import com.moli.schedule.springtask.schedule.task.CronTaskRegistrar;
import com.moli.schedule.springtask.schedule.task.SchedulingRunnable;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author moli
 * @time 2024-07-25 17:26:01
 */
@Service("sysJobService")
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

    @Resource
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    @Transactional
    public boolean addJob(SysJobSaveOrUpdateDTO job) {
        SysJob sysJob = BeanUtil.toBean(job, SysJob.class);
        // 保存 job
        boolean success = this.save(sysJob);
        if (!success) return false;

        // 保存成功，并且状态为正常运行，创建任务
        if (SysJobStatusEnum.RUNNABLE.getStatus().equals(sysJob.getStatus())) {
            SchedulingRunnable runnable = new SchedulingRunnable(
                    sysJob.getBeanName(),
                    sysJob.getMethodName(),
                    sysJob.getMethodParams()
            );
            cronTaskRegistrar.addCronTask(runnable, sysJob.getCron());
        }
        return true;
    }

    @Override
    @Transactional
    public boolean removeJob(Long id) {
        // 查询对应的 job
        SysJob sysJob = this.getById(id);
        if (Objects.isNull(sysJob)) return false;

        // 删除表中数据
        boolean success = this.removeById(id);
        if (!success) return false;

        // 删除定时任务
        if (SysJobStatusEnum.RUNNABLE.getStatus().equals(sysJob.getStatus())) {
            SchedulingRunnable runnable = new SchedulingRunnable(
                    sysJob.getBeanName(),
                    sysJob.getMethodName(),
                    sysJob.getMethodParams()
            );
            cronTaskRegistrar.removeCronTask(runnable);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateJob(SysJobSaveOrUpdateDTO job) {
        SysJob frontJob = this.getById(job.getId());
        // 如果不存在，则添加
        if (Objects.isNull(frontJob)) {
            SysJobService jobService = (SysJobService) AopContext.currentProxy();
            return jobService.addJob(job);
        }

        SysJob afterJob = BeanUtil.toBean(job, SysJob.class);
        // 更新
        boolean success = this.updateById(afterJob);
        if (!success) return false;
        // 删除之前的任务
        if (SysJobStatusEnum.RUNNABLE.getStatus().equals(frontJob.getStatus())) {
            SchedulingRunnable fontRun = new SchedulingRunnable(
                    frontJob.getBeanName(),
                    frontJob.getMethodName(),
                    frontJob.getMethodParams()
            );
            cronTaskRegistrar.removeCronTask(fontRun);
        }

        if (SysJobStatusEnum.RUNNABLE.getStatus().equals(frontJob.getStatus())) {
            // 添加新的任务
            SchedulingRunnable afterRun = new SchedulingRunnable(
                    afterJob.getBeanName(),
                    afterJob.getMethodName(),
                    afterJob.getMethodParams()
            );
            cronTaskRegistrar.addCronTask(afterRun, job.getCron());
        }
        return true;
    }

    @Override
    public SysJobVO getJobById(Long id) {
        SysJob sysJob = this.getById(id);
        if (Objects.isNull(sysJob)) return null;
        return BeanUtil.toBean(sysJob, SysJobVO.class);
    }
}
