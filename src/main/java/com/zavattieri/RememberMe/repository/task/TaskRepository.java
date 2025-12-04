package com.zavattieri.RememberMe.repository.task;

import com.zavattieri.RememberMe.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdAndUserId(Long id, Long userId); //JPA query method to find a task by its ID and associated user ID
                                                                //Optional is used to handle the case where the task might not be found
    List<Task> findAllByUserId(Long userId);


}
