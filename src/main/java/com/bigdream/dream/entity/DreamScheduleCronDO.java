package com.bigdream.dream.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-30 20:18
 **/
@Data
public class DreamScheduleCronDO implements Serializable {
    private static final long serialVersionUID = 7257512514191600041L;
    private String id;                  // 主键id
    private String taskId;              // 任务id
    private String taskName;            // 任务名称
    private String cron;                // cron表达式
    private Date crtTime;                // 创建时间
    private Date updTimie;                // 更新时间
}
