/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sharkchili.queueSync.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.core.date.DateTime;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.json.JSONUtil;
import com.sharkchili.queueSync.config.QueueBean;
import com.sharkchili.queueSync.task.ConsumerTask;
import com.sharkchili.queueSync.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@Slf4j
public class BasicController {

    @Autowired
    private QueueBean queueBean;

    private Snowflake snowflake = new Snowflake();

    private static ThreadPoolExecutor threadPoolExecutor = ExecutorBuilder.create()
            .setCorePoolSize(Runtime.getRuntime().availableProcessors())
            .setMaxPoolSize(Runtime.getRuntime().availableProcessors() << 2)
            .setThreadFactory(new NamedThreadFactory("consumerTask-", false))
            .build();


    @PostConstruct
    private void init() {
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(new ConsumerTask());
        }
    }

    @PostMapping("/submitTask")
    public String submitTask() {
        Task task = new Task();
        long id = snowflake.nextId();
        task.setId(id);
        task.setTaskName("任务-" + id);
        task.setParams(new JSONObject().putOnce("userName", RandomUtil.randomString(5)));
        task.setCreateTime(new DateTime());
        task.setFinishTime(new DateTime());

        log.info("提交任务:{}", JSONUtil.toJsonStr(task));
        queueBean.put(task);
        return "success";
    }


}
