package com.zavattieri.RememberMe.dto.task;

import com.zavattieri.RememberMe.domain.task.TaskCategory;
import com.zavattieri.RememberMe.domain.task.TaskStatus;

import java.time.LocalDateTime;

public record TaskUpdateDTO(
        String title,
        String description,
        TaskCategory taskCategory,
        LocalDateTime dueDate,
        TaskStatus status
) {
}
