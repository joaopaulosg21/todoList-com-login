package aprendendo.api.toDoList.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import aprendendo.api.toDoList.model.Task;
import aprendendo.api.toDoList.model.User;
import aprendendo.api.toDoList.repository.TaskRepository;
import aprendendo.api.toDoList.service.TaskService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);


        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskRepository.findAllByUserId(anyLong())).thenReturn(tasks);
        when(taskRepository.findByIdAndUserId(anyLong(),anyLong())).thenReturn(Optional.of(task));
        when(taskRepository.findAllByUserIdAndCompleted(anyLong(),anyBoolean())).thenReturn(tasks);
    }

    @Test
    public void addTask() {

        Task newTask = taskService.addTask(user, task);

        Assertions.assertEquals(task.getName(),newTask.getName());
        Assertions.assertEquals(task.getUser().getEmail(),newTask.getUser().getEmail());
    }

    @Test
    public void tasksByUser() {
        Task newTask = taskService.addTask(user, task);

        List<Task> userTasks = taskService.findMyTasks(user);

        Assertions.assertEquals(newTask.getName(),userTasks.get(0).getName());
        Assertions.assertEquals(newTask.getUser().getEmail(),userTasks.get(0).getUser().getEmail());
    }
    
    @Test
    public void completeTask() {

        Task responseTask = taskService.completeTask(user, 1);

        Assertions.assertTrue(responseTask.isCompleted());

    }

    @Test
    public void completedTasks() {

        taskService.addTask(user, task);
        taskService.completeTask(user, 1);
        List<Task> completedTasks = taskService.findCompletedTasks(user);

        Assertions.assertTrue(completedTasks.get(0).isCompleted());
    }
}
