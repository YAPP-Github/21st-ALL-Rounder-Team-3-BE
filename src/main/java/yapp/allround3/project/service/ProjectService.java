package yapp.allround3.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.repository.MemberRepository;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final ParticipantRepository participantRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public Project save(Project project) {
		return projectRepository.save(project);
	}

	public Member findMember(Long memberId){
		return memberRepository.findById(memberId)
			.orElseThrow();
	}

	public Participant findParticipantById(Long participantId){
		return participantRepository.findParticipantById(participantId);
	}



	public List<Project> findProjectByMember(Member member) {
		List<Participant> participants = participantRepository.findByMember(member);

		return participants.stream()
			.map(Participant::getProject)
			.toList();
	}

    public Project findProjectById(Long projectId) {
		Optional<Project> project = projectRepository.findById(projectId);
		return project.orElseThrow(()->new CustomException("존재하지 않는 프로젝트입니다."));
    }

	//TODO Participant 조회 부분 의존성 정리
	public Long findMyParticipantId(Member member,Project project){
		return participantRepository.findParticipantByProjectAndMember(project,member).getId();
	}

	public int findParticipantCountByProject(Project project){
		return participantRepository.countParticipantByProject(project);
	}
}
