package yapp.allround3.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import yapp.allround3.member.domain.Member;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.project.domain.Project;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	List<Participant> findByMember(Member member);

	@Query("select p from Participant p join fetch p.member")
	List<Participant> findParticipantsByProject(Project project);
}
