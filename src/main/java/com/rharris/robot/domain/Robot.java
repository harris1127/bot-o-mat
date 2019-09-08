package com.rharris.robot.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Robot")
public class Robot {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "typeId")
    private int robotTypeId;

    @Transient
    @JsonInclude()
    private List<DefinedTasks> tasks;

    public Robot() {};

    public Robot(int id, String name, int robotTypeId, List<DefinedTasks> tasks) {
        this.id = id;
        this.name = name;
        this.robotTypeId = robotTypeId;
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRobotTypeId() {
        return robotTypeId;
    }

    public void setRobotTypeId(int robotTypeId) {
        this.robotTypeId = robotTypeId;
    }

    public List<DefinedTasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<DefinedTasks> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", robotTypeId=" + robotTypeId +
                ", tasks=" + tasks +
                '}';
    }
}
