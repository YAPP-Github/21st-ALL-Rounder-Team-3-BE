package yapp.allround3.participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.domain.Project;
import yapp.allround3.task.domain.Task;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {
	private final ParticipantRepository participantRepository;


	public List<Participant> findParticipantsByProject(Project project){
		return participantRepository.findParticipantsByProject(project);
	}

	public Participant findParticipantById(Long id){
		return participantRepository.findParticipantById(id);
	}

	public int findParticipantCountByProject(Project project){
		return participantRepository.countParticipantByProject(project);
	}

	public List<Participant> findParticipantsGivenFeedback(Task task) {
		return participantRepository.findParticipantsGivenFeedback(task);
	}
}
