package com.zavattieri.RememberMe.domain.task;

import com.zavattieri.RememberMe.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "tasks")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskCategory category;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne //many tasks can belong to one user -> relationship between Task and User
    @JoinColumn(name = "user_id") //foreign key column in the tasks table
    private User user;

   // Custom constructor without id, status and user
    public Task (String title, String description, TaskCategory category, LocalDateTime dueDate, User user) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.dueDate = dueDate;
        this.status = TaskStatus.PENDING; //default status when creating a new task
        this.user = user;
    }
}
