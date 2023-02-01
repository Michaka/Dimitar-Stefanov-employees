package com.poc.employees.response.model;

import com.poc.employees.DTO.PairDTO;
import com.poc.employees.model.Employee;
import com.poc.employees.model.Pair;

import java.util.List;
import java.util.Set;

public class ResponseEmployee {
    private String message;
    private PairDTO employees;

    public ResponseEmployee(String message, PairDTO employees) {
        this.message = message;
        this.employees = employees;
    }
    public ResponseEmployee(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public PairDTO getEmployees(){return employees;}

    public void setMessage(String message) {
        this.message = message;
    }
}
