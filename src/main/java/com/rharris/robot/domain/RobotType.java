package com.rharris.robot.domain;

import javax.persistence.*;

@Entity
@Table(name = "RobotType")
public class RobotType {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "view")
    private String view;

    public RobotType() {};

    public RobotType(int id, String name, String view) {
        this.id = id;
        this.name = name;
        this.view = view;
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

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "RobotType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", view='" + view + '\'' +
                '}';
    }
}
