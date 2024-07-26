package com.moli.schedule.springtask.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moli.schedule.springtask.schedule.entity.SysJob;
import com.moli.schedule.springtask.schedule.entity.dto.SysJobSaveOrUpdateDTO;
import com.moli.schedule.springtask.schedule.entity.vo.SysJobVO;

/**
 * @author moli
 * @time 2024-07-25 17:25:46
 */
public interface SysJobService extends IService<SysJob> {

    /**
     * 添加任务
     */
    boolean addJob(SysJobSaveOrUpdateDTO job);

    /**
     * 删除任务
     */
    boolean removeJob(Long id);

    /**
     * 更新任务
     */
    boolean updateJob(SysJobSaveOrUpdateDTO job);

    /**
     * 根据 id 获取 job
     */
    SysJobVO getJobById(Long id);
}
