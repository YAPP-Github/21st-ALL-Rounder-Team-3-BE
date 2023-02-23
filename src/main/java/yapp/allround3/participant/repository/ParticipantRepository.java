package yapp.allround3.participant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yapp.allround3.member.domain.Member;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.project.domain.Project;
import yapp.allround3.task.domain.Task;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	// @Query("SELECT p from Participant p join fetch p.member where p.id=:id")
	Optional<Participant> findParticipantById(Long id);

	List<Participant> findByMember(Member member);

	@Query("select p from Participant p join fetch p.member where p.project = :project")
	List<Participant> findParticipantsByProject(Project project);

	int countParticipantByProject(Project project);


	int countParticipantByProjectId(Long projectId);
	Optional<Participant> findParticipantByProjectAndMember(Project project,Member member);

	Optional<Participant> findByMemberAndProject(Member member, Project project);
}
