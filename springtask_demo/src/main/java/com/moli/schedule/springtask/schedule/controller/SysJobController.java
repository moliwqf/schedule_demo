package com.moli.schedule.springtask.schedule.controller;

import com.moli.schedule.common.ReturnData;
import com.moli.schedule.springtask.schedule.config.groups.Insert;
import com.moli.schedule.springtask.schedule.config.groups.Update;
import com.moli.schedule.springtask.schedule.entity.dto.SysJobSaveOrUpdateDTO;
import com.moli.schedule.springtask.schedule.entity.vo.SysJobVO;
import com.moli.schedule.springtask.schedule.service.SysJobService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.moli.schedule.common.Constants.*;

/**
 * @author moli
 * @time 2024-07-25 17:25:15
 * @description sys job 实现调度
 */
@RestController
@RequestMapping("/job")
public class SysJobController {

    @Resource
    private SysJobService sysJobService;

    @PostMapping("/addJob")
    public ReturnData<?> addJob(@RequestBody @Validated({Insert.class}) SysJobSaveOrUpdateDTO job) {
        boolean success = sysJobService.addJob(job);
        return success ? ReturnData.ok(INSERT_SUCCESS) : ReturnData.fail(INSERT_FAIL);
    }

    @DeleteMapping("/removeJob")
    public ReturnData<?> removeJob(@RequestParam("id") Long id) {
        boolean success = sysJobService.removeJob(id);
        return success ? ReturnData.ok() : ReturnData.fail();
    }

    @PutMapping("/updateJob")
    public ReturnData<?> updateJob(@RequestBody @Validated({Update.class}) SysJobSaveOrUpdateDTO job) {
        boolean success = sysJobService.updateJob(job);
        return success ? ReturnData.ok(UPDATE_SUCCESS) : ReturnData.fail(UPDATE_FAIL);
    }

    @GetMapping("/getJobById")
    public ReturnData<SysJobVO> getJobById(@RequestParam("id") Long id) {
        SysJobVO job = sysJobService.getJobById(id);
        return ReturnData.ok(job);
    }
}
