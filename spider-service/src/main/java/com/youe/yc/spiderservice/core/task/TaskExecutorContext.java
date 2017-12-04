package com.youe.yc.spiderservice.core.task;

/**
 * 任务执行上下文
 * @author ibm
 *
 */
public class TaskExecutorContext {
	private String contextId;

	public TaskExecutorContext(String contextId) {
		super();
		this.contextId = contextId;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}
	
	@Override
	public int hashCode() {
		return contextId.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TaskExecutorContext){
			TaskExecutorContext temp = (TaskExecutorContext) obj;
			return this.contextId.equals(temp.contextId);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return contextId;
	}
	
	public static void main(String[] args) {
	}
}
