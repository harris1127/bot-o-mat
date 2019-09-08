package com.rharris.robot.scheduler;
import com.rharris.robot.domain.TaskView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class RunnableRobotTask implements Callable<TaskView> {

    private final static Logger LOGGER = LoggerFactory.getLogger(RunnableRobotTask.class);

    private String name;
    private int taskId;
    private String task;
    private int duration;

    public RunnableRobotTask(String name, int taskId, String task, int duration) {
        this.name = name;
        this.taskId = taskId;
        this.task = task;
        this.duration = duration;
    }

    @Override
    public TaskView call() throws Exception {

        List<String> logMessages = new ArrayList<>();
        TaskView taskView = new TaskView();
        taskView.setTaskId(taskId);

        LOGGER.info(name + " is starting to " + task);
        logMessages.add(name + " is starting to " + task);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            LOGGER.info(name + " will finish this task in " + duration + " ms");
            logMessages.add(name + " will finish this task in " + duration + " ms");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            logMessages.add(e.getMessage());
            return taskView;
        }

        stopWatch.stop();
        LOGGER.info(name + " took " + stopWatch.getTotalTimeSeconds() + " to complete " + task);
        taskView.setResult(name + " took " + stopWatch.getTotalTimeSeconds() + " to complete " + task);
        taskView.setMessage(logMessages);
        return taskView;

    }
}
