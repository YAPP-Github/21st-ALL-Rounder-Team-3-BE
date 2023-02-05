package yapp.allround3.task.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.domain.TaskContent;

public interface TaskContentRepository extends JpaRepository<TaskContent, Long> {
    public List<TaskContent> findAllByTask(Task task);
}