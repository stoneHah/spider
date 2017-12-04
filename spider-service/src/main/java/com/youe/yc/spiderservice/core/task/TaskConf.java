package com.youe.yc.spiderservice.core.task;

/**
 * 任务配置信息
 * @author ibm
 *
 */
public class TaskConf {
	private int thread = 5;
	
	public TaskConf(CrawTaskConfBuilder builder){
		this.thread = builder.thread;
	}
	
	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public static class CrawTaskConfBuilder{
		private int thread;
		
		public CrawTaskConfBuilder(){
		}
		
		public CrawTaskConfBuilder thread(int thread){
			this.thread = thread;
			return this;
		}
		
		public TaskConf build(){
			return new TaskConf(this);
		}
	}
}
