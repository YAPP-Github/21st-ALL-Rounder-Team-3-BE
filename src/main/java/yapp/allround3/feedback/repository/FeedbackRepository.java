package yapp.allround3.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import yapp.allround3.feedback.domain.Feedback;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.task.domain.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
	List<Feedback> findByTask(Task task);

	Optional<Feedback> findByTaskAndParticipant(Task task, Participant participant);
}
