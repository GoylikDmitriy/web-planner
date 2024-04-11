package com.goylik.planner.service.impl;

import com.goylik.planner.model.dto.EmployeeDto;
import com.goylik.planner.model.dto.TaskDto;
import com.goylik.planner.model.entity.Employee;
import com.goylik.planner.model.entity.Task;
import com.goylik.planner.repository.EmployeeRepository;
import com.goylik.planner.service.exception.EmployeeNotFoundException;
import com.goylik.planner.service.PlannerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PlannerServiceImpl implements PlannerService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployees() {
        log.info("Getting all employees.");
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(this::mapEmployee)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> getTasksForEmployee(long employeeId, LocalDate startDate, LocalDate endDate) {
        log.info("Getting tasks for employee with id = {}", employeeId);
        Employee employee = fetchEmployeeById(employeeId);
        var tasks = getTasksInDateRange(employee.getTasks(), startDate, endDate);

        return tasks.stream()
                .map(this::mapTask)
                .toList();
    }

    private Employee fetchEmployeeById(long id) {
        log.info("Fetching employee by id.");
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Can't find employee with id = {}", id);
                    return new EmployeeNotFoundException("Can't find employee with id = " + id);
                });
    }

    private List<Task> getTasksInDateRange(List<Task> tasks, LocalDate startDate, LocalDate endDate) {
        log.info("Filtering tasks in a date range. start date = {}, end date = {}", startDate, endDate);
        return tasks.stream()
                .filter(t -> isTaskInDateRange(t, startDate))
                .toList();
    }

    private boolean isTaskInDateRange(Task task, LocalDate startDate) {
        return task.getStartDate().isEqual(startDate) ||
                task.getStartDate().isAfter(startDate);
    }

    private EmployeeDto mapEmployee(Employee employee) {
        log.info("Mapping employee = {}", employee);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private TaskDto mapTask(Task task) {
        log.info("Mapping task = {}", task);
        return modelMapper.map(task, TaskDto.class);
    }
}
