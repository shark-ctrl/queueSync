package com.sharkchili.queueSync.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * 要被执行的任务
 */
@Data
public class Task {

    /**
     * 任务id
     */
    private Long id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 请求参数
     */
    private JSONObject params;
    /**
     * 创建时间
     */
    private DateTime createTime;
    /**
     * 结束时间
     */
    private DateTime finishTime;
}
