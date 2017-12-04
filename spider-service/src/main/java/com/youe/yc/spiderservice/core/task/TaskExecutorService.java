package com.youe.yc.spiderservice.core.task;


import com.youe.yc.spiderservice.core.exception.TaskExecuteException;

/**
 * 任务执行池
 * @author ibm
 *
 */
public interface TaskExecutorService {
	
	/**
	 * 执行任务
	 * @param task
	 */
	void execute(Task task, TaskExecutorContext executorContext) throws TaskExecuteException;
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	boolean pause(Task task, TaskExecutorContext executorContext) throws TaskExecuteException;
	
	/**
	 * 
	 * @param task
	 * @return
	 */
	boolean restart(Task task, TaskExecutorContext executorContext) throws TaskExecuteException;
	
	/**
	 * 
	 * @param task
	 * @param executorContext
	 * @return
	 */
	boolean stop(Task task, TaskExecutorContext executorContext) throws TaskExecuteException;
	
	/**
	 * 统计总的任务数
	 * @return
	 */
	int countTotalTasks();
	
	/**
	 * 统计上下文下的任务数
	 * @param executorContext
	 * @return
	 */
	int countTotalTasks(TaskExecutorContext executorContext);

	/**
	 * 统计指定任务状态的任务数
	 * @param taskStatus
	 * @return
	 */
	int countTaskByStatus(Task.TaskStatus taskStatus);
	
	/**
	 * 统计上下文下置顶任务状态的任务数
	 * @param executorContext
	 * @param taskStatus
	 * @return
	 */
	int countTaskByStatus(TaskExecutorContext executorContext, Task.TaskStatus taskStatus);
	
	int countExecutorService();
}
