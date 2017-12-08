package com.youe.yc.spiderclient.spidertask.entity;

import com.google.common.hash.HashCode;
import com.youe.yc.common.utils.IdGenerator;

import java.util.Date;

/**
 *
 */
public class Task {
    private TaskIdentifier taskIdentifier;
    private TaskInfo taskInfo;
    private volatile TaskStatus taskStatus;

    private String name;

    private Date createTime;
    private Date runTime;
    private Date completeTime;

    public Task(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;

        this.taskIdentifier = new TaskIdentifier(IdGenerator.getId());
        this.taskStatus = TaskStatus.Create;
        this.createTime = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
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

    public TaskIdentifier getTaskIdentifier() {
        return taskIdentifier;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public TaskInfo.TaskContext getTaskContext(){
        return taskInfo.getTaskContext();
    }

    @Override
    public int hashCode() {
        return taskIdentifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task) {
            Task temp = (Task) obj;
            return temp.taskIdentifier.equals(this.taskIdentifier);
        }
        return super.equals(obj);
    }

    public static enum TaskStatus {
        Create(0), Running(1), Pause(2), Complete(3),
        /**
         * 待执行
         */
        ToRunning(4);
        private int code;

        TaskStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static TaskStatus getByCode(int code) {
            TaskStatus[] taskStatuses = values();

            for (TaskStatus taskStatus : taskStatuses) {
                if (taskStatus.code == code) {
                    return taskStatus;
                }
            }

            return null;
        }
    }

    public static class TaskIdentifier{
        private String id;

        public TaskIdentifier(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Override
        public int hashCode() {
            return HashCode.fromString(id).asInt();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TaskIdentifier) {
                TaskIdentifier temp = (TaskIdentifier) obj;
                return temp.getId().equals(this.getId());
            }
            return false;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
