package com.sharkchili.queueSync.task;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.sharkchili.queueSync.config.QueueBean;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者
 */
@Slf4j
public class ConsumerTask implements Runnable {


    @Override
    public void run() {
        QueueBean queueBean = SpringUtil.getBean(QueueBean.class);
        while (true) {
            //从阻塞队列中获取任务
            Task task = queueBean.take();
            log.info("消费者消费任务，任务详情:{}", JSONUtil.toJsonStr(task));

        }
    }
}
