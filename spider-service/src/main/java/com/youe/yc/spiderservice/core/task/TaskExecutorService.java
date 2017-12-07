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
	 * @param taskInfo
	 */
	void execute(TaskInfo taskInfo) throws TaskExecuteException;
	
	/**
	 * 
	 * @param taskIdentifier
	 * @return
	 */
	boolean pause(Task.TaskIdentifier taskIdentifier) throws TaskExecuteException;
	
	/**
	 * 
	 * @param taskIdentifier
	 * @return
	 */
	boolean restart(Task.TaskIdentifier taskIdentifier) throws TaskExecuteException;
	
	/**
	 * 
	 * @param taskIdentifier
	 * @return
	 */
	boolean stop(Task.TaskIdentifier taskIdentifier) throws TaskExecuteException;
	
}
