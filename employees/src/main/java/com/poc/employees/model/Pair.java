package com.poc.employees.model;

public class Pair {
    private Employee firstEmployee;
    private Employee secondEmployee;

    public Pair(Employee firstEmployee, Employee secondEmployee){
        this.firstEmployee = firstEmployee;
        this.secondEmployee = secondEmployee;
    }
    public Employee getFirstEmployee() {
        return firstEmployee;
    }

    public Employee getSecondEmployee() {
        return secondEmployee;
    }
}
