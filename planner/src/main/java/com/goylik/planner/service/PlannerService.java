package com.goylik.planner.service;

import com.goylik.planner.model.dto.EmployeeDto;
import com.goylik.planner.model.dto.TaskDto;

import java.time.LocalDate;
import java.util.List;

public interface PlannerService {
    List<EmployeeDto> getEmployees();
    List<TaskDto> getTasksForEmployee(long employeeId, LocalDate startDate, LocalDate endDate);
}
