package com.youe.yc.spiderservice.core.task;

import java.util.List;

public interface ListableTaskExecutorService extends CountableTaskExecutorService {
    /**
     * 获取指定taskContext下的任务信息
     * @param taskContext
     * @return
     */
    List<Task> getTasks(TaskInfo.TaskContext taskContext);
}
