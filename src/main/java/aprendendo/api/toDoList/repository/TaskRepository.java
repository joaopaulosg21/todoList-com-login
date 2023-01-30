package aprendendo.api.toDoList.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aprendendo.api.toDoList.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>{
    Optional<Task> findByUserId(long userId);

    Optional<Task> findByIdAndUserId(long id,long userId);

    List<Task> findAllByUserId(long userId);

    List<Task> findAllByUserIdAndCompleted(long userId,boolean completed);
}
