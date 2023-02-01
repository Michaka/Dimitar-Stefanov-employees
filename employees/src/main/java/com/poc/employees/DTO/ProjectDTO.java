package com.poc.employees.DTO;

import java.time.LocalDate;
import java.time.Period;

public class ProjectDTO {
    private int id;
    private LocalDate dateFrom;
    private  LocalDate dateTo;

    public ProjectDTO (int id, LocalDate dateFrom, LocalDate dateTo){
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
        return Period.between(dateFrom,dateTo).getDays();
    }
}
