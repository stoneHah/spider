package com.youe.yc.spiderclient.spidertask.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 爬取任务信息
 *
 * @author ibm
 */
public class TaskInfo {
    private String templateId;

    /**
     * 是否是增量任务
     */
    private boolean increment;

    private TaskContext taskContext;

    /**
     * 任务启动线程数
     */
    private int threadNum = 5;

    public TaskInfo(String templateId) {
        this(templateId, TaskContext.GLOBAL_CONTEXT);
    }

    public TaskInfo(String templateId, TaskContext taskContext) {
        this(templateId, false, taskContext);
    }

    public TaskInfo(String templateId, boolean increment, TaskContext taskContext) {
        this.templateId = templateId;
        this.increment = increment;
        this.taskContext = taskContext;
    }

    public String getTemplateId() {
        return templateId;
    }


    public boolean isIncrement() {
        return increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }

    public TaskContext getTaskContext() {
        return taskContext == null ? TaskContext.GLOBAL_CONTEXT : taskContext;
    }

    public void setTaskContext(TaskContext taskContext) {
        this.taskContext = taskContext;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        if (threadNum <= 0) {
            throw new IllegalArgumentException("threadNum should bigger than zero");
        }
        this.threadNum = threadNum;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public static class TaskContext{
        public static final TaskContext GLOBAL_CONTEXT = new TaskContext("TASKCONTEXT_GLOBAL");

        private String id;

        public TaskContext(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TaskContext) {
                TaskContext temp = (TaskContext) obj;
                return temp.getId().equals(this.getId());
            }
            return false;
        }
    }
}
