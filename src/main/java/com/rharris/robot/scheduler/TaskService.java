package com.rharris.robot.scheduler;

import com.rharris.robot.domain.Robot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final static Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    public List<RunnableRobotTask> createTasks(Robot robot) {

        List<RunnableRobotTask> tasks = new ArrayList<>();

        robot.getTasks().forEach(definedTasks -> {

            RunnableRobotTask runnableRobotTask =
                    new RunnableRobotTask(robot.getName(), definedTasks.getId(),
                            definedTasks.getDescription(), definedTasks.getEta());
            tasks.add(runnableRobotTask);

        });

        return tasks;
    }
}
