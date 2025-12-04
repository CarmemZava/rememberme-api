package com.zavattieri.RememberMe.dto.task;

import com.zavattieri.RememberMe.domain.task.TaskCategory;
import com.zavattieri.RememberMe.domain.task.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskResponseDTO(  //DTO used to send task data in responses related to CRUD operations
        @NotNull Long id,
        @NotBlank String title,
        String description,
        @NotNull TaskCategory category,
        LocalDateTime dueDate,
        @NotNull TaskStatus status
        ) {
}
