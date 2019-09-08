package com.rharris.robot.controller;

import com.rharris.robot.domain.DefinedTasks;
import com.rharris.robot.domain.Robot;
import com.rharris.robot.domain.TaskView;
import com.rharris.robot.repository.DefinedTasksRepository;
import com.rharris.robot.repository.RobotRepository;
import com.rharris.robot.scheduler.RunnableRobotTask;
import com.rharris.robot.scheduler.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
public class RobotController {

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private DefinedTasksRepository definedTasksRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/home")
    public String view() {
        return "index";
    }

    @PostMapping(value = "/createTask")
    public ResponseEntity<List<TaskView>> createTasks(@RequestBody Robot robot) throws InterruptedException, ExecutionException {

        List<Future<TaskView>> futureList = null;
        List<TaskView> taskViewList = new ArrayList<>();
        List<DefinedTasks> definedTasks = definedTasksRepository.findAll();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Collections.shuffle(definedTasks); //randomize list of tasks to assigned robot

        robot.setTasks(definedTasks.subList(0, 5)); // 5 tasks per robot

        List<RunnableRobotTask> robotAssignedTasks = taskService.createTasks(robot);
        futureList = executorService.invokeAll(robotAssignedTasks);

        if (futureList.stream().allMatch(Future::isDone)) {

            for (Future<TaskView> taskViewFuture : futureList) {

                taskViewList.add(taskViewFuture.get());
            }

            return new ResponseEntity<>(taskViewList, HttpStatus.CREATED);

        } else {

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }

    }

    @PostMapping(value = "/saveRobot")
    public ResponseEntity<String> saveRobot(@RequestBody Robot robot) {

        if (robot != null) {
            robot.setId(new Random().nextInt());
            robotRepository.save(robot);
            return new ResponseEntity<String>(HttpStatus.CREATED);
        }

        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/deleteTasks")
    public ResponseEntity<String> deleteTask(@RequestBody int[] taskId){

        for(int task: taskId){

            definedTasksRepository.deleteById(task);
        }

        return new ResponseEntity<String>(HttpStatus.CREATED);

    }
}
