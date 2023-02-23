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
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantService {

    private final MemberService memberService;
    private final ParticipantRepository participantRepository;
    private final ProjectService projectService;
    private final FeedbackRepository feedbackRepository;
    private final TaskRepository taskRepository;

    /**
     * 조회 로직
     */
    public List<Participant> findParticipantsByProject(Project project) {

        return participantRepository.findByProjectAndParticipantStatus(project, ParticipantStatus.NORMAL);
    }

    public List<Participant> findParticipantsByProject(Long projectId) {
        return participantRepository.findByProjectIdAndParticipantStatus(projectId, ParticipantStatus.NORMAL);
    }

    public Participant findParticipantById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(()->new CustomException("해당 task의 참여자가 아닙니다."));
    }

    public int findParticipantCountByProjectId(Long projectId) {
        return participantRepository.countParticipantByProjectId(projectId);
    }


    public Participant findParticipantByProjectAndMember(Project project, Member member) {
        return participantRepository.findByProjectAndMember(project, member)
            .orElseThrow(() -> new CustomException("프로젝트에 참여한 멤버가 아닙니다."));
    }

    public Participant findParticipantByProjectAndMember(Long projectId, Long memberId) {
        return participantRepository.findByProjectIdAndMemberId(projectId, memberId)
            .orElseThrow(() -> new CustomException("프로젝트에 참여한 멤버가 아닙니다."));
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

        List<Participant> unConfirmedParticipants = participantRepository.findByProjectAndParticipantStatus(
            project, ParticipantStatus.NORMAL
        );
        unConfirmedParticipants.removeAll(confirmedParticipants);
        unConfirmedParticipants.remove(participant);

        return ParticipantFeedbackResponse.of(confirmedParticipants, unConfirmedParticipants);
    }

    /**
     * 비즈니스 로직
     */
    @Transactional
    public void joinProject(Long projectId, Long memberId) {
        Member member = memberService.findMemberById(memberId);
        Project project = projectService.findProjectById(projectId);

        Participant participant = participantRepository.findByMemberAndProject(member, project)
            .orElseGet(() -> Participant.of(project, member));

        if (participant.getParticipantStatus() == ParticipantStatus.NORMAL) {
            throw new CustomException("이미 가입된 참여자에요");
        }

        participant.join();

        // 피드백 필요 인원 증가
        taskRepository.findTasksByProjectId(projectId)
            .forEach(Task::addFeedbackRequiredPersonnel);
    }

    @Transactional
    public void withdrawProject(Long participantId) {
        Participant participant = findParticipantById(participantId);
        participant.withdraw();

        // 리더일 경우 다른 사람에게 리더 넘기기
        if (participant.isLeader()) {
            Project project = participant.getProject();
            List<Participant> participants = findParticipantsByProject(project)
                .stream()
                .filter(Predicate.not(Predicate.isEqual(participant)))
                .toList();
            if (participants.size() > 0) {
                participant.changeLeader(participants.get(0));
            }
            else {
                participant.getProject().delete();
            }
        }

        // 피드백 필요 인원 감축
        taskRepository.findTasksByProjectId(participant.getProject().getId())
            .forEach(Task::subtractFeedbackRequiredPersonnel);
    }

    @Transactional
    public void withdrawAllProjects(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        List<Participant> participants = participantRepository.findByMemberAndParticipantStatus(
            member, ParticipantStatus.NORMAL
        );
        participants.stream()
            .map(Participant::getId)
            .forEach(this::withdrawProject);
    }

    @Transactional
    public void changeLeader(Long memberId, Long participantId) {
        Participant former = participantRepository.findById(participantId)
                .orElseThrow(() -> new CustomException("해당 참여자가 존재하지 않습니다."));

        Member member = memberService.findMemberById(memberId);
        Project project = former.getProject();
        Participant participant = participantRepository
                .findByProjectAndMember(project, member)
                .orElseThrow(() -> new CustomException("같은 프로젝트가 아닙니다."));

        participant.changeLeader(former);
    }
}
