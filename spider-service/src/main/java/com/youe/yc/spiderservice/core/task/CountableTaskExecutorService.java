package com.youe.yc.spiderservice.core.task;

import java.util.Map;

public interface CountableTaskExecutorService extends TaskExecutorService {
    /**
     * 统计总的任务数
     *
     * @return
     */
    long countTotalTasks();

    /**
     * 获取此上下文中 任务数
     * @param taskContext
     * @return
     */
    long countTotalTasks(TaskInfo.TaskContext taskContext);

    /**
     * 根据状态获取 任务数
     *
     * @param taskStatus
     * @return
     */
    long countTasksByStatus(Task.TaskStatus taskStatus);

    /**
     * 按任务状态进行分组统计
     *
     * @return
     */
    Map<Task.TaskStatus, Long> countTasksGroupByStatus();
}
