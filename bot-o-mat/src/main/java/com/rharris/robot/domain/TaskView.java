package com.rharris.robot.domain;

import java.util.List;

public class TaskView {

    private int taskId;
    private String result;
    private List<String> message;

    public TaskView(){}

    public TaskView(List<String> message, String result, int taskId) {
        this.message = message;
        this.result = result;
        this.taskId = taskId;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "TaskView{" +
                "taskId=" + taskId +
                ", result='" + result + '\'' +
                ", message=" + message +
                '}';
    }

}
