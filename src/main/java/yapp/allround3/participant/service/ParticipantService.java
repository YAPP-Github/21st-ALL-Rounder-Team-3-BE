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
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.domain.ParticipantStatus;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.repository.TaskRepository;
import java.util.List;

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
                .orElseThrow(()->new CustomException("해당 task의 참여자가 아닙니다."));
    }

    public int findParticipantCountByProjectId(Long projectId) {

        return participantRepository.countParticipantByProjectId(projectId);
    }

    @Transactional
    public void joinProject(Long projectId, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        Project project = projectService.findProjectById(projectId);

        Participant participant = participantRepository.findByMemberAndProject(member, project)
            .orElseGet(() -> Participant.of(project, member));

        if (participant.getParticipantStatus() == ParticipantStatus.NORMAL) {
            throw new CustomException("이미 가입된 참여자에요");
        }

        participant.joinProject();
        participantRepository.save(participant);

        taskRepository.findTasksByProjectId(projectId)
            .forEach(task -> {
                    task.addFeedbackRequiredPersonnel();
                    taskRepository.save(task);
                }
            );
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

    public Participant findParticipantByProjectAndMember(Project project, Member member) {
        return participantRepository.findParticipantByProjectAndMember(project, member)
                .orElseThrow(() -> new CustomException("프로젝트에 참여한 멤버가 아닙니다."));
    }
}
