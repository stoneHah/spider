package com.youe.yc.spiderservice.core.task;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务执行上下文
 *
 * @author ibm
 */
public class TaskExecutorContext {
	private TaskList taskList = new TaskList();

	private ConcurrentHashMap<Task.TaskIdentifier, Task> taskIdentifierToTaskMap = new ConcurrentHashMap<>();

	public void addTask(Task task) {
		taskList.addTask(task);
		taskIdentifierToTaskMap.put(task.getTaskIdentifier(), task);
	}

	public Task getTask(Task.TaskIdentifier taskIdentifier) {
		return taskIdentifierToTaskMap.get(taskIdentifier);
	}

	public List<Task> getAllTasks(){
		return taskList.getTaskList();
	}

	public static void main(String[] args) {
	}
}
