package yapp.allround3.participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yapp.allround3.member.repository.MemberRepository;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.repository.ProjectRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {

	private final ProjectRepository projectRepository;
	private final ParticipantRepository participantRepository;
	private final MemberRepository memberRepository;

	public List<Participant> findParticipantsByProject(Project project){
		return participantRepository.findParticipantsByProject(project);
	}

	public Participant findParticipantById(Long id){
		return participantRepository.findParticipantById(id);
	}

	public int findParticipantCountByProject(Project project){
		return participantRepository.countParticipantByProject(project);
	}
}
