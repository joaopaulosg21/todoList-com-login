package aprendendo.api.toDoList.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import aprendendo.api.toDoList.model.Task;
import aprendendo.api.toDoList.model.User;
import aprendendo.api.toDoList.repository.TaskRepository;

@Service
public class TaskService {
    
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(User user,Task task) {
        task.setUser(user);
        task.setCompleted(false);
        task.setCreationDate(LocalDate.now());
        return taskRepository.save(task);
    }

    public List<Task> findMyTasks(User user) {
        return taskRepository.findAllByUserId(user.getId());
    }

    public Task completeTask(User user,long id) {
        Optional<Task> optionalTask = taskRepository.findByIdAndUserId(id, user.getId());

        if(optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setCompleted(true);
            task.setCompletedDate(LocalDate.now());
            taskRepository.save(task);
            return task;
        }
        throw new RuntimeException("Task not found");
    }
}
