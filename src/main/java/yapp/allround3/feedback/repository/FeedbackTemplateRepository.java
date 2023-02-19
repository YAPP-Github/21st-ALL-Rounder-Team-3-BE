package yapp.allround3.feedback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yapp.allround3.feedback.domain.FeedbackTemplate;
import yapp.allround3.task.domain.Task;

@Repository
public interface FeedbackTemplateRepository extends JpaRepository<FeedbackTemplate, Long> {

	Optional<FeedbackTemplate> findByTaskAndTemplateKey(Task task, int templateKey);

	List<FeedbackTemplate> findByTask(Task task);
}
