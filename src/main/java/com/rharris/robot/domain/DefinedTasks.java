package com.rharris.robot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Tasks")
public class DefinedTasks {

    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "eta")
    private int eta;

    public DefinedTasks() {}

    public DefinedTasks(int id, String description, int eta) {
        this.id = id;
        this.description = description;
        this.eta = eta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", eta=" + eta +
                '}';
    }
}
