package com.sharkchili.queueSync.config;

import cn.hutool.core.thread.ExecutorBuilder;
import com.sharkchili.queueSync.task.Task;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列
 */
@Component
@Slf4j
public class QueueBean {

    private BlockingQueue<Task> blockingQueue = new ArrayBlockingQueue<>(2000);


    @SneakyThrows
    public void put(Task task) {
        blockingQueue.put(task);
    }

    @SneakyThrows
    public Task take() {
        return blockingQueue.take();
    }


}
