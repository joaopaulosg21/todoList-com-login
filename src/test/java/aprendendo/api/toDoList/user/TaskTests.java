package aprendendo.api.toDoList.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import aprendendo.api.toDoList.model.Task;
import aprendendo.api.toDoList.model.User;
import aprendendo.api.toDoList.repository.TaskRepository;
import aprendendo.api.toDoList.service.TaskService;

@ExtendWith(MockitoExtension.class)
public class TaskTests {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    private Task task;

    private User user;

    @BeforeEach
    public void init() {
        taskService = new TaskService(taskRepository);
        
        user = new User(1L,"teste task","testeTask@email.com","123",null);
        task = new Task();
        task.setName("Test task");
        task.setCompleted(false);
        task.setCreationDate(LocalDate.now());
        task.setUser(user);

        when(taskRepository.save(any(Task.class))).thenReturn(task);
    }

    @Test
    public void addTask() {

        Task newTask = taskService.addTask(user, task);

        Assertions.assertEquals(task.getName(),newTask.getName());
        Assertions.assertEquals(task.getUser().getEmail(),newTask.getUser().getEmail());
    }
    
}
