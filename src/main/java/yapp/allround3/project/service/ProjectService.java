package yapp.allround3.project.service;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import yapp.allround3.common.exception.CustomException;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.repository.ParticipantRepository;
import yapp.allround3.project.controller.dto.ProjectRequest;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.repository.ProjectRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ParticipantRepository participantRepository;
    private final MemberService memberService;

    @Transactional
    public Project saveProject(Project project, Long memberId) {
        Project saveProject = projectRepository.save(project);
        Member member = memberService.findMemberById(memberId);

        Participant.initProject(saveProject, member);

        return saveProject;
    }

    public List<Project> findProjectByMember(Member member) {
        List<Participant> participants = participantRepository.findByMember(member);

        return participants.stream()
                .map(Participant::getProject)
                .toList();
    }

    public Project findProjectById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return project.orElseThrow(() -> new CustomException("존재하지 않는 프로젝트입니다."));
    }

    public Long findMyParticipantId(Member member, Project project) {
        return participantRepository.findParticipantByProjectAndMember(project, member).orElseThrow().getId();
    }

    public int findParticipantCountByProject(Project project) {
        return participantRepository.countParticipantByProject(project);
    }

    @Transactional
    public void updateProject(Long projectId, ProjectRequest projectRequest) {
        Project project = findProjectById(projectId);
        project.updateProjectStatus(projectRequest.getProjectStatus());
        project.updateDifficulty(projectRequest.getDifficulty());
        project.updateDueDate(projectRequest.getDueDate());
        project.updateStartDate(projectRequest.getStartDate());
        project.updateGoal(projectRequest.getGoal());
        project.updateName(projectRequest.getName());
        log.info(project.toString());
        projectRepository.save(project);
    }
}
