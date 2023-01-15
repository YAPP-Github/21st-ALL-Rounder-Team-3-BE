package yapp.allround3.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yapp.allround3.feedback.domain.Feedback;
import yapp.allround3.task.domain.Task;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
	List<Feedback> findByTask(Task task);
}
