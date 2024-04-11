package com.goylik.planner.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("last_name")
    private String lastName;
}
