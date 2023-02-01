package com.poc.employees.model;

import java.util.Map;

public class Employee {
    private int id;
    private Map<Integer,Project> projects;
    public Employee(int id, Map<Integer,Project> projects){
        this.id = id;
        this.projects = projects;
    }

    public int getId() {
        return id;
    }

    public Map<Integer,Project> getProjects(){
        return projects;
    }
    public void addProject(Project project){
        this.projects.put(project.getId(),project);
    }
}
