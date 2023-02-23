package yapp.allround3.participant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yapp.allround3.member.domain.Member;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.domain.ParticipantStatus;
import yapp.allround3.project.domain.Project;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	List<Participant> findByMemberAndParticipantStatus(Member member, ParticipantStatus participantStatus);

	List<Participant> findByProjectAndParticipantStatus(Project project, ParticipantStatus participantStatus);
	List<Participant> findByProjectIdAndParticipantStatus(Long projectId, ParticipantStatus participantStatus);

	int countParticipantByProjectId(Long projectId);
	Optional<Participant> findByProjectAndMember(Project project, Member member);
	Optional<Participant> findByProjectIdAndMemberId(Long projectId, Long memberId);

	Optional<Participant> findByMemberAndProject(Member member, Project project);
}
