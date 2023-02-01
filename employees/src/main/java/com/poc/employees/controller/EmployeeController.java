package com.poc.employees.controller;

import com.poc.employees.DTO.PairDTO;
import com.poc.employees.helper.ReadCSV;
import com.poc.employees.response.model.ResponseEmployee;
import com.poc.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path="/api/employees", produces="application/json")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping(value = "/upload",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ResponseEmployee> uploadFile(@RequestPart("file") MultipartFile file) {
        String message = "";

        if (ReadCSV.isCSV(file)) {
            try {
                PairDTO employees = employeeService.findPair(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseEmployee(message,employees));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseEmployee(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseEmployee(message));
    }
}
