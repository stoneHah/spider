package com.youe.yc.spiderservice.core.task;

import com.youe.yc.common.utils.IdGenerator;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 爬取任务信息
 * @author ibm
 *
 */
public class Task {
	private String taskId;
	private String templateId;
	private TaskStatus taskStatus;
	private Date createTime;
	private Date completeTime;
	
	private ConcurrentHashMap<String,Object> extraMap = new ConcurrentHashMap<>();
	
	public Task(String templateId) {
		super();
		this.templateId = templateId;
		this.taskId = IdGenerator.getId();
		this.createTime = new Date();
		this.taskStatus = TaskStatus.Create;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public Date getCreateTime() {
		return createTime;
	}
	
	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	
	public void put(String key,Object value){
		extraMap.put(key, value);
	}
	
	public Object get(String key){
		return extraMap.get(key);
	}

	@Override
	public int hashCode() {
		return taskId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Task){
			Task t = (Task) obj;
			return this.taskId.equals(t.taskId);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public static enum TaskStatus{
		Create,Running,Pause,Exception,Complete
	}
}
