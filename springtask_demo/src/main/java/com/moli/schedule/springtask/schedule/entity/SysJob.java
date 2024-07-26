package com.moli.schedule.springtask.schedule.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.moli.schedule.springtask.schedule.enums.SysJobStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author moli
 * @time 2024-07-25 17:22:29
 * @description 任务
 */
@Data
@TableName("sys_job")
@AllArgsConstructor
@NoArgsConstructor
public class SysJob implements Serializable {
    /**
     * 任务ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * bean名称
     */
    private String beanName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法参数
     */
    private String methodParams;
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 状态（1正常 0暂停）
     * @see SysJobStatusEnum
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否删除
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDelete;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}

