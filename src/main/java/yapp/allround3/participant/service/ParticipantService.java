package yapp.allround3.participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yapp.allround3.common.exception.CustomException;
import yapp.allround3.feedback.domain.Feedback;
import yapp.allround3.feedback.repository.FeedbackRepository;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;
import yapp.allround3.participant.controller.dto.ParticipantFeedbackResponse;
import yapp.allround3.participant.controller.dto.ParticipantResponse;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.repository.TaskRepository;
import yapp.allround3.task.service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {

	private final MemberService memberService;
	private final ParticipantRepository participantRepository;
	private final ProjectService projectService;
	private final FeedbackRepository feedbackRepository;
	private final TaskRepository taskRepository;

	public List<Participant> findParticipantsByProject(Project project) {

		return participantRepository.findParticipantsByProject(project);
	}

	public List<Participant> findParticipantsByProjectId(Long projectId) {
		Project project = projectService.findProjectById(projectId);

		return participantRepository.findParticipantsByProject(project);
	}

	public Participant findParticipantById(Long id) {

		return participantRepository.findParticipantById(id)
			.orElseThrow(CustomException::new);
	}

	public int findParticipantCountByProject(Project project) {

		return participantRepository.countParticipantByProject(project);
	}

	public List<Participant> findParticipantsGivenFeedback(Task task) {

		return participantRepository.findParticipantsGivenFeedback(task);
	}

	@Transactional
	public Participant joinProject(Long projectId, Long memberId) {
		Member member = memberService.findMemberById(memberId);
		Project project = projectService.findProjectById(projectId);

		Participant participant = Participant.from(project, member);

		return participantRepository.save(participant);
	}

	@Transactional
	public void changeLeader(Long memberId, Long participantId) {
		Participant former = participantRepository.findById(participantId)
			.orElseThrow(() -> new CustomException("해당 참여자가 존재하지 않습니다."));

		Member member = memberService.findMemberById(memberId);
		Project project = former.getProject();
		Participant participant = participantRepository
			.findParticipantByProjectAndMember(project, member)
			.orElseThrow(() -> new CustomException("같은 프로젝트가 아닙니다."));

		participant.changeLeader(former);
	}

	public Participant findParticipantByTaskAndMember(Task task, Member member) {
		List<Participant> participants = findParticipantsGivenFeedback(task);

		return participants.stream()
			.filter(participant -> Objects.equals(participant.getMember(), member))
			.findFirst()
			.orElseThrow(CustomException::new);
	}

	public ParticipantFeedbackResponse findParticipantGroupByTask(Long taskId) {
		Task task = taskRepository.findById(taskId)
			.orElseThrow(() -> new CustomException("해당 테스크가 없습니다."));

		Participant participant = task.getParticipant();
		Project project = participant.getProject();

		List<Feedback> feedbacks = feedbackRepository.findByTask(task);
		List<Participant> confirmedParticipants = feedbacks.stream()
			.map(Feedback::getParticipant)
			.toList();

		List<Participant> unConfirmedParticipants = participantRepository.findParticipantsByProject(project);
		unConfirmedParticipants.removeAll(confirmedParticipants);
		unConfirmedParticipants.remove(participant);

		return ParticipantFeedbackResponse.of(confirmedParticipants, unConfirmedParticipants);
	}
}
