package com.rharris.robot.controller;

import com.rharris.robot.domain.DefinedTasks;
import com.rharris.robot.domain.Robot;
import com.rharris.robot.domain.RobotType;
import com.rharris.robot.repository.DefinedTasksRepository;
import com.rharris.robot.repository.RobotRepository;
import com.rharris.robot.repository.RobotTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RobotRestController {

    @Autowired
    private DefinedTasksRepository definedTasksRepository;

    @Autowired
    private RobotTypeRepository robotTypeRepository;

    @Autowired
    private RobotRepository robotRepository;

    @GetMapping(value = "/getAllTasks")
    public List<DefinedTasks> getAllTasks() {
        return definedTasksRepository.findAll();
    }

    @GetMapping(value = "/getAllTypes")
    public List<RobotType> getAllTypes() {
        return robotTypeRepository.findAll();
    }

    @GetMapping(value = "/getAllRobots")
    public List<Robot> getAllRobots() {
        return robotRepository.findAll();
    }
}
