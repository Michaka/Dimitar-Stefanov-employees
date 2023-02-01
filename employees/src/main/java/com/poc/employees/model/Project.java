package com.poc.employees.model;


import java.time.Duration;
import java.time.LocalDate;

public class Project {

    private int id;
    private LocalDate dateFrom;
    private  LocalDate dateTo;

    public Project (int id, LocalDate dateFrom, LocalDate dateTo){
        this.id = id;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public int getId() {
        return id;
    }

    public long getDaysBetween(){
        return Duration.between(dateFrom,dateTo).toDays();
    }
}
