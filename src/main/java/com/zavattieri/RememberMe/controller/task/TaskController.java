package com.zavattieri.RememberMe.controller.task;

import com.zavattieri.RememberMe.dto.task.TaskCreateDTO;
import com.zavattieri.RememberMe.dto.task.TaskResponseDTO;
import com.zavattieri.RememberMe.dto.task.TaskUpdateDTO;
import com.zavattieri.RememberMe.service.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasksByUser(){
        List<TaskResponseDTO> taskList = taskService.getAllTasksByUser();
        return ResponseEntity.ok(taskList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id){
        TaskResponseDTO taskById = taskService.getTaskById(id);

        return ResponseEntity.ok(taskById);
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskCreateDTO data) { // Endpoint to create a new task, it expects a TaskCreateDTO object in the request body which is validated
      TaskResponseDTO task = taskService.createTask(data); // Call the service to create a new task using the provided data

        return ResponseEntity.ok(task);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO data){
        TaskResponseDTO updatedTask = taskService.updateTask(id, data);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);

        return ResponseEntity.ok("Task deleted");
    }


}
