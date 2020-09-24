package com.todo.core;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "tasks")

@NamedQueries(
        {
                @NamedQuery(
                        name = "com.todo.core.Task.findAll",
                        query = "SELECT t FROM Task t"
                ),
                @NamedQuery(
                        name = "com.todo.core.Task.deleteById",
                        query = "delete from Task t where t.id = :id"
                )
        })



public class Task {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String name;
    private String description;
    @OneToMany(mappedBy = "parent",fetch = FetchType.EAGER,cascade = javax.persistence.CascadeType.REMOVE)
    @Cascade({CascadeType.DELETE,CascadeType.SAVE_UPDATE, CascadeType.ALL})
    private List<SubTask> subtasks = new ArrayList<SubTask>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", subtasks=" + subtasks +
                '}';
    }
}
