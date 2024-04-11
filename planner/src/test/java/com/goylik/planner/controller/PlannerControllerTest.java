package com.goylik.planner.controller;

import com.goylik.planner.model.dto.EmployeeDto;
import com.goylik.planner.model.dto.TaskDto;
import com.goylik.planner.service.PlannerService;
import com.goylik.planner.service.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlannerController.class)
public class PlannerControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlannerService plannerService;

    private EmployeeDto employeeDto;
    private TaskDto taskDto;

    @BeforeEach
    public void setup() {
        taskDto = new TaskDto();
        taskDto.setId(1);
        taskDto.setTask("Задача 1");
        taskDto.setStartDate(LocalDate.of(2024, 4, 1));
        taskDto.setEndDate(LocalDate.of(2024, 4, 5));
        taskDto.setCompletionDate(null);
        taskDto.setCompleted(false);

        employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setLastName("Иванов");
    }

    @Test
    public void testGetEmployees() throws Exception {
        List<EmployeeDto> employees = Collections.singletonList(employeeDto);

        when(plannerService.getEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/planner/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo((int) employeeDto.getId())))
                .andExpect(jsonPath("$[0].last_name", equalTo(employeeDto.getLastName())));
    }

    @Test
    public void testGetTasksForEmployee() throws Exception {
        long employeeId = 1;
        LocalDate startDate = LocalDate.of(2024, 4, 1);
        LocalDate endDate = LocalDate.of(2024, 4, 5);

        List<TaskDto> tasks = Collections.singletonList(taskDto);

        when(plannerService.getTasksForEmployee(employeeId, startDate, endDate)).thenReturn(tasks);

        mockMvc.perform(get("/api/planner/tasks?employeeId=1&startDate=2024-04-01&endDate=2024-04-05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo((int) taskDto.getId())))
                .andExpect(jsonPath("$[0].task", equalTo(taskDto.getTask())))
                .andExpect(jsonPath("$[0].start_date", equalTo(taskDto.getStartDate()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))))
                .andExpect(jsonPath("$[0].end_date", equalTo(taskDto.getEndDate()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))))
                .andExpect(jsonPath("$[0].completion_date", equalTo(taskDto.getCompletionDate())))
                .andExpect(jsonPath("$[0].completed", equalTo(taskDto.isCompleted())));
    }

    @Test
    public void testGetTasksForEmployeeNotFoundException() throws Exception {
        long employeeId = 2;
        LocalDate startDate = LocalDate.of(2024, 4, 1);
        LocalDate endDate = LocalDate.of(2024, 4, 5);

        when(plannerService.getTasksForEmployee(employeeId, startDate, endDate))
                .thenThrow(new EmployeeNotFoundException("Employee not found."));

        mockMvc.perform(get("/api/planner/tasks?employeeId=2&startDate=2024-04-01&endDate=2024-04-05"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Employee not found."));
    }
}