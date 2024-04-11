package com.goylik.planner.controller;

import com.goylik.planner.model.dto.EmployeeDto;
import com.goylik.planner.model.dto.TaskDto;
import com.goylik.planner.service.PlannerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/planner")
@AllArgsConstructor
@Slf4j
public class PlannerController {
    private final PlannerService plannerService;

    @GetMapping("/employees")
    public List<EmployeeDto> getEmployees() {
        log.info("GET REQUEST. Getting all employees.");
        return plannerService.getEmployees();
    }

    @GetMapping("/tasks")
    public List<TaskDto> getTasksForEmployee(@RequestParam("employeeId") long employeeId,
                                             @RequestParam("startDate")
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                             @RequestParam("endDate")
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("GET REQUEST. Getting tasks for employee with id = {}, start date = {}, end date = {}.",
                employeeId, startDate, endDate);
        return plannerService.getTasksForEmployee(employeeId, startDate, endDate);
    }
}
