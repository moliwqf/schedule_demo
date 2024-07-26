package com.moli.schedule.springtask.schedule.entity.dto;

import com.moli.schedule.springtask.schedule.config.groups.Insert;
import com.moli.schedule.springtask.schedule.config.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author moli
 * @time 2024-07-26 08:52:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysJobSaveOrUpdateDTO implements Serializable {

    /**
     * 任务ID
     */
    @NotNull(groups = {Update.class})
    private Long id;
    /**
     * bean名称
     */
    @NotBlank(message = "beanName 不能为空", groups = {Insert.class})
    private String beanName;
    /**
     * 方法名称
     */
    @NotBlank(message = "methodName 不能为空", groups = {Insert.class})
    private String methodName;
    /**
     * 方法参数
     */
    private String methodParams;
    /**
     * cron表达式
     */
    @NotBlank(message = "cron 不能为空", groups = {Insert.class})
    private String cron;
    /**
     * 状态（1正常 0暂停）
     */
    @NotNull(groups = {Insert.class})
    @Pattern(regexp = "[0-1]")
    private Integer status;
    /**
     * 备注
     */
    private String remark;
}
