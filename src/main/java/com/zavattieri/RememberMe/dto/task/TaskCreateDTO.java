package com.zavattieri.RememberMe.dto.task;

import com.zavattieri.RememberMe.domain.task.TaskCategory;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TaskCreateDTO( //DTO used to receive task data when creating a new task (front-end to back-end)
        @NotBlank String title,
        String description,
        TaskCategory taskCategory,
        LocalDateTime dueDate
) {
}
