package com.poc.employees.service;

import com.poc.employees.DTO.PairDTO;
import com.poc.employees.DTO.ProjectDTO;
import com.poc.employees.helper.ReadCSV;
import com.poc.employees.model.Employee;
import com.poc.employees.model.Pair;
import com.poc.employees.model.Project;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {

    /** find the pair of employees who have worked
        together on common projects for the longest period of time.*/
    public PairDTO findPair(MultipartFile file) {
        try {
            List<Employee> employees = ReadCSV.csvToEmployees(file.getInputStream());

            return getLongestWorkingPair(removeExtraProjects(generatePairs(employees)));
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    /** Generates all possible pairs of employees without duplicates */
    private Set<Pair> generatePairs(List<Employee> employees){
        Set<Pair> pairs = new HashSet<>();

        // Make all possible pairs
        for (int firstPointer = 0; firstPointer<employees.size(); firstPointer++ ) {
            Employee firstEmployee = employees.get(firstPointer);
            for (int secondPointer = firstPointer; secondPointer<employees.size(); secondPointer++) {
                Employee secondEmployee = employees.get(secondPointer);
                if(firstEmployee.getId()!= secondEmployee.getId()){
                    Pair pair = new Pair(firstEmployee,secondEmployee);
                    pairs.add(pair);
                }
            }
        }
        return pairs;
    }

    /** Creates set of PairDTO containing only projects that are same for both employees
     *  and counts the total days they worked together */
    private Set<PairDTO> removeExtraProjects(Set<Pair> pairs){
        Set<PairDTO> pairDTOS = new HashSet<>();
        for(Pair pair: pairs){
            Map<Integer,Project> firstEmployeeProjects = pair.getFirstEmployee().getProjects();
            Map<Integer,Project> secondEmployeeProjects = pair.getSecondEmployee().getProjects();
            Set<ProjectDTO> commonProject = new HashSet<>();
            if(firstEmployeeProjects.size()>secondEmployeeProjects.size()){
                commonProject = getCommonProjects(secondEmployeeProjects,firstEmployeeProjects);
            }else {
                commonProject = getCommonProjects(firstEmployeeProjects,secondEmployeeProjects);
            }
            pairDTOS.add(new PairDTO(
                    pair.getFirstEmployee().getId(),
                    pair.getSecondEmployee().getId(),
                    commonProject,
                    countWorkPeriod(commonProject)));

        }
        return pairDTOS;
    }

    /** Filter only the projects common for both employees changes dateFrom and dateTo to show the period
     * both employees worked together on the project  */
    private Set<ProjectDTO> getCommonProjects(Map<Integer,Project> firstEmployeeProjects, Map<Integer,Project> secondEmployeeProjects){

        Set<ProjectDTO> commonProjects = new HashSet<>();
        for(Integer projectId:firstEmployeeProjects.keySet()){
            if(secondEmployeeProjects.containsKey(projectId)){
                ProjectDTO projectDTO = countDaysWorkedTogetherOnProject(
                        firstEmployeeProjects.get(projectId),
                        secondEmployeeProjects.get(projectId));
                if(projectDTO!=null) {
                    commonProjects.add(projectDTO);
                }
            }
        }
        return commonProjects;
    }

    /** changes dateFrom and dateTo to show the period
     * both employees worked together on the project */
    private ProjectDTO countDaysWorkedTogetherOnProject(Project firstEmployeeProject, Project secondEmployeeProject){
        LocalDate dateFrom = secondEmployeeProject.getDateFrom();
        LocalDate dateTo = secondEmployeeProject.getDateTo();
        int projectId = firstEmployeeProject.getId();
        if(firstEmployeeProject.getDateFrom().isAfter(dateFrom)){
            dateFrom = firstEmployeeProject.getDateFrom();
        }
        if(firstEmployeeProject.getDateTo().isBefore(dateTo)){
            dateTo =firstEmployeeProject.getDateTo();
        }
        if(dateFrom.isAfter(dateTo)){
            return null;
        }
        return new ProjectDTO(projectId,dateFrom,dateTo);
    }

    /** sums days from all the projects both employees worked on */
    private long countWorkPeriod (Set<ProjectDTO> projects){
        long workPeriod =0;
        for(ProjectDTO project: projects){
            workPeriod += project.getDaysBetween();
        }
        return workPeriod;
    }

    /** finds the pair of employees working for the longest period of time on the same project*/
    private PairDTO getLongestWorkingPair(Set<PairDTO> pairs){
        PairDTO pairDTO = null;
        long longestPeriod =0;
        for(PairDTO pair : pairs){
            if(longestPeriod<pair.getWorkedPeriod()){
                longestPeriod = pair.getWorkedPeriod();
                pairDTO = pair;
            }
        }
        return pairDTO;
    }

}
