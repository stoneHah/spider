package com.youe.yc.spiderservice.core.task;

import java.util.*;


public class TaskList implements Iterable<Task> {
	private List<Task> taskList = Collections.synchronizedList(new ArrayList<>());

	public TaskList() {
	}

	public void addTask(Task task) {
		taskList.add(task);
	}

	public int size() {
		return taskList.size();
	}

	/**
	 * 统计不同状态下的任务数
	 *
	 * @return
	 */
	public Map<Task.TaskStatus, Integer> countByStatus() {
		//TODO
		return null;
	}

	public List<Task> getTaskList(){
		return taskList;
	}

	@Override

	public Iterator<Task> iterator() {
		return taskList.iterator();
	}


}
