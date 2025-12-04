package com.zavattieri.RememberMe.service.task;

import com.zavattieri.RememberMe.domain.task.Task;
import com.zavattieri.RememberMe.domain.user.User;
import com.zavattieri.RememberMe.dto.task.TaskCreateDTO;
import com.zavattieri.RememberMe.dto.task.TaskResponseDTO;
import com.zavattieri.RememberMe.dto.task.TaskUpdateDTO;
import com.zavattieri.RememberMe.repository.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TaskService { //This service will handle CRUD operations for tasks
    @Autowired
    TaskRepository taskRepository;

    public User getAuthenticatedUser() { //method to get the authenticated user from the security context
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public List<TaskResponseDTO> getAllTasksByUser(){

        // 1. Find the user with SecurityContextHolder
        User user = getAuthenticatedUser();

        //2. Retrieve all tasks associated with the user from the database using the user id
        List<Task> tasksOfUser = taskRepository.findAllByUserId(user.getId());

        // 3. Convert the list of Task entities to TaskResponseDTO and return it
        return tasksOfUser.stream() //.stream() creates a stream from the list of Task entities
                .map(task -> new TaskResponseDTO( //.map transforms each Task entity into a TaskResponseDTO
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getCategory(),
                        task.getDueDate(),
                        task.getStatus()
                ))
                .toList(); //.toList() collects the transformed elements into a new List of TaskResponseDTOs, converts again to list
    }

    public TaskResponseDTO getTaskById(Long id){

        // 1. Find the user with SecurityContextHolder
        User user = getAuthenticatedUser();

        //2. Retrieve the task from the database using the task
        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        // 3. Convert the Task entity to TaskResponseDTO and return it
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCategory(),
                task.getDueDate(),
                task.getStatus()
        );
    }

    public TaskResponseDTO createTask(TaskCreateDTO data) {

        // 1. Find the user with SecurityContextHolder
        User user = getAuthenticatedUser();

        // 2. Create a new Task entity using the data from TaskCreateDTO and associate it with the user, I had to add a custom constructor in Task entity
        Task newTask = new Task(
                                data.title(),
                                data.description(),
                                data.taskCategory(),
                                data.dueDate(),
                                user);

        // 3. Save the new task to the database
        Task savedTask = taskRepository.save(newTask);

        // 4. Convert the saved Task entity to TaskResponseDTO and return it
        return new TaskResponseDTO(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getCategory(),
                savedTask.getDueDate(),
                savedTask.getStatus()
        );

    }

    public TaskResponseDTO updateTask(Long id, TaskUpdateDTO data){

        // 1. Find the user with SecurityContextHolder
        User user = getAuthenticatedUser();

        //2. Retrieve the existing task from the database using the task and user Ids
        Task existingTask = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        if(data.title() != null && data.title().trim().isEmpty()){ //Additional validation to prevent empty titles
            throw new RuntimeException("Title cannot be empty");
        }

        if (data.title() != null) {
            existingTask.setTitle(data.title());
        }
        if (data.description() != null) {
            existingTask.setDescription(data.description());
        }
        if(data.taskCategory() != null){
            existingTask.setCategory(data.taskCategory());
        }
        if(data.dueDate() != null){
            existingTask.setDueDate(data.dueDate());
        }
        if(data.status() != null){
            existingTask.setStatus(data.status());
        }

        // 3. Save the updated task back to the database
        Task updatedTask = taskRepository.save(existingTask);

        // 4. Convert the updated Task entity to TaskResponseDTO and return it
        return new TaskResponseDTO(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getCategory(),
                updatedTask.getDueDate(),
                updatedTask.getStatus()
        );
    }

    public void deleteTask(Long id){
        //1. Find the user with SecurityContextHolder
        User user = getAuthenticatedUser();

        //2. Retrieve the existing task from the database using the task and user Ids
        Task task = taskRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        //3. Delete the task from the database
        taskRepository.delete(task);
    }


}
