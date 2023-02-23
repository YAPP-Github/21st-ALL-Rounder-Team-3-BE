package yapp.allround3.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import yapp.allround3.participant.domain.Participant;
import yapp.allround3.task.domain.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByParticipant(Participant participant);
    Optional<Task> findTaskById(Long id);

    @Modifying
    @Query("update Task t set t.feedbackRequiredPersonnel=t.feedbackRequiredPersonnel+1 where t.id = :taskId")
    void updateTaskFeedbackRequiredPersonnel(@Param("taskId") Long taskId);

    @Query("select t from Task t where t.participant.project.id = :projectId")
    List<Task> findTasksByProjectId(@Param("projectId") Long projectId);
}
