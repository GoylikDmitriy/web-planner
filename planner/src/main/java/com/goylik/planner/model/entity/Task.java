package com.goylik.planner.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "task")
    private String task;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "completion_date")
    private LocalDate completionDate;
    @Column(name = "completed")
    private boolean isCompleted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", completionDate=" + completionDate +
                ", isCompleted=" + isCompleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return id == task1.id && isCompleted == task1.isCompleted && task.equals(task1.task) && startDate.equals(task1.startDate) && endDate.equals(task1.endDate) && Objects.equals(completionDate, task1.completionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task, startDate, endDate, completionDate, isCompleted);
    }
}
