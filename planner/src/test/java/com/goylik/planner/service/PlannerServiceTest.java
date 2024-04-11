package com.goylik.planner.service;

import com.goylik.planner.model.dto.EmployeeDto;
import com.goylik.planner.model.dto.TaskDto;
import com.goylik.planner.model.entity.Employee;
import com.goylik.planner.model.entity.Task;
import com.goylik.planner.repository.EmployeeRepository;
import com.goylik.planner.service.exception.EmployeeNotFoundException;
import com.goylik.planner.service.impl.PlannerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlannerServiceTest {
    @InjectMocks
    private PlannerServiceImpl plannerService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ModelMapper modelMapper;

    private Employee employee;
    private Task task;
    private EmployeeDto employeeDto;
    private TaskDto taskDto;

    @BeforeEach
    public void setup() {
        task = new Task();
        task.setId(1);
        task.setTask("Задача 1");
        task.setStartDate(LocalDate.of(2024, 4, 1));
        task.setEndDate(LocalDate.of(2024, 4, 5));
        task.setCompletionDate(null);
        task.setCompleted(false);

        taskDto = new TaskDto();
        taskDto.setId(1);
        taskDto.setTask("Задача 1");
        taskDto.setStartDate(LocalDate.of(2024, 4, 1));
        taskDto.setEndDate(LocalDate.of(2024, 4, 5));
        taskDto.setCompletionDate(null);
        taskDto.setCompleted(false);

        employee = new Employee();
        employee.setId(1);
        employee.setLastName("Иванов");
        employee.setFirstName("Иван");
        employee.setMiddleName("Иванович");
        employee.setTasks(Collections.singletonList(task));

        employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setLastName("Иванов");
    }

    @BeforeEach
    public void setupEmployeeDto() {
        employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setLastName("Иванов");
    }

    @Test
    public void testGetEmployees() {
        List<Employee> employees = Collections.singletonList(employee);

        when(employeeRepository.findAll()).thenReturn(employees);
        when(modelMapper.map(employee, EmployeeDto.class)).thenReturn(employeeDto);

        List<EmployeeDto> result = plannerService.getEmployees();

        verify(employeeRepository, times(1)).findAll();

        assertEquals(1, result.size());
        assertEquals(employeeDto, result.get(0));
    }

    @Test
    public void testGetTasksForEmployee() {
        long employeeId = 1;
        LocalDate startDate = LocalDate.of(2024, 4, 1);
        LocalDate endDate = LocalDate.of(2024, 4, 5);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);

        List<TaskDto> result = plannerService.getTasksForEmployee(employeeId, startDate, endDate);

        verify(employeeRepository, times(1)).findById(employeeId);

        assertEquals(1, result.size());
        assertEquals(taskDto, result.get(0));
    }

    @Test
    public void testGetTasksForEmployeeNotFoundException() {
        long employeeId = 2;
        LocalDate startDate = LocalDate.of(2024, 4, 1);
        LocalDate endDate = LocalDate.of(2024, 4, 5);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () ->
                plannerService.getTasksForEmployee(employeeId, startDate, endDate));

        verify(employeeRepository, times(1)).findById(employeeId);
    }
}
