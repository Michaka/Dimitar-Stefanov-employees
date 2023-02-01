package com.poc.employees.DTO;

import java.util.Set;

public class PairDTO {
    private int firstEmployeeId;
    private int secondEmployeeId;
    private Set<ProjectDTO> projects;
    private long workedPeriod;

    public PairDTO(int firstEmployeeId, int secondEmployeeId, Set<ProjectDTO> projects, long workedPeriod){
        this.firstEmployeeId = firstEmployeeId;
        this.secondEmployeeId =secondEmployeeId;
        this.projects = projects;
        this.workedPeriod = workedPeriod;

    }

    public int getFirstEmployeeId() {
        return firstEmployeeId;
    }

    public int getSecondEmployeeId() {
        return secondEmployeeId;
    }

    public Set<ProjectDTO> getProjects() {
        return projects;
    }

    public long getWorkedPeriod() {
        return workedPeriod;
    }
}
