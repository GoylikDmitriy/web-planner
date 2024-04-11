package com.goylik.planner.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("task")
    private String task;
    @JsonProperty("start_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;
    @JsonProperty("end_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;
    @JsonProperty("completion_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate completionDate;
    @JsonProperty("completed")
    private boolean isCompleted;
}
