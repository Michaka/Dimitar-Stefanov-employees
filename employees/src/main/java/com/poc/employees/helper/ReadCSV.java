package com.poc.employees.helper;

import com.poc.employees.model.Employee;
import com.poc.employees.model.Project;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadCSV {
    public static String TYPE = "text/csv";
    static String[] HEADERS = { "EmpID", "ProjectID", "DateFrom", "DateTo"};

    /** Check if it is valid CSV */
    public static boolean isCSV(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    /** Convert csv file to List of Employees */
    public static List<Employee> csvToEmployees(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Project> projects = new ArrayList<>();
            Map<Integer, Map<Integer,Project>> employees = new HashMap<>();


            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Project project = new Project(
                        Integer.parseInt(csvRecord.get("ProjectID")),
                        parseDate(csvRecord.get("DateFrom")),
                        parseDate(csvRecord.get("DateTo"))
                );
                int employeeId = Integer.parseInt(csvRecord.get("EmpID"));
                Map<Integer,Project> employeeProjects = employees.get(employeeId);
                if(employeeProjects==null){
                    employeeProjects = new HashMap<>();
                }
                updateEmployeeProjects(employeeProjects, csvRecord);
                employees.put(employeeId,employeeProjects);
            }

            return createEmployeeListFromMap(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    /** Parsing date from string to LocalDate */
    public static LocalDate parseDate(String rawDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

        if(rawDate == null || rawDate.equals("NULL")){
            LocalDate date =LocalDate.now();
            date.format(formatter);
            return date;
        }else {
            return LocalDate.parse(rawDate, formatter);
        }

    }

    /** Parsing project information from csv in Project object and adding it to existing employee projects */
    private static Map<Integer,Project>  updateEmployeeProjects(Map<Integer,Project> projects, CSVRecord csvRecord){
        Project project = new Project(
                Integer.parseInt(csvRecord.get("ProjectID")),
                parseDate(csvRecord.get("DateFrom")),
                parseDate(csvRecord.get("DateTo"))
        );
        projects.put(project.getId(),project);
        return projects;
    }

    /** Create list of all employees and there projects */
    private static List<Employee> createEmployeeListFromMap(Map<Integer, Map<Integer,Project>> employees){
        List<Employee> employeeList = new ArrayList<>();
        for(Integer employeeId : employees.keySet()){
            Employee employee = new Employee(employeeId, employees.get(employeeId));
            employeeList.add(employee);
        }

        return employeeList;
    }
}
