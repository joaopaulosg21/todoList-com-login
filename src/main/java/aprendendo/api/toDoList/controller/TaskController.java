package aprendendo.api.toDoList.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aprendendo.api.toDoList.model.Task;
import aprendendo.api.toDoList.model.User;
import aprendendo.api.toDoList.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @PostMapping
    public ResponseEntity<Task> addTask(@Valid @RequestBody Task task,@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(user, task));
    }
    
    @GetMapping("/my")
    public ResponseEntity<List<Task>> findMyTasks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.findMyTasks(user));
    }

}
