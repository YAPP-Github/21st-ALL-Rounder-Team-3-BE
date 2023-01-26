package yapp.allround3.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.security.SecurityUtils;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;
import yapp.allround3.participant.controller.dto.ParticipantDto;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.project.controller.dto.ProjectResponse;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;
import yapp.allround3.task.controller.dto.TaskResponse;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final MemberService memberService;
    private final ProjectService projectService;
    private final TaskService taskService;

    private final ParticipantService participantService;


    @ResponseBody
    @GetMapping("/projects")
    public CustomResponse<List<ProjectResponse>> findProjectsByMember(
            HttpServletRequest request
    ){

        Long memberId = (Long)request.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);
        List<Project> projects = projectService.findProjectByMember(member);
        List<ProjectResponse> responses = projects.stream()
                .map(project -> {
                    List<ParticipantDto> participantDtos = participantService
                            .findParticipantsByProject(project).stream()
                            .map(ParticipantDto::of)
                            .toList();
                    //추후 내 task 조회를 위해 나의 해당 프로젝트 참여자 id 필드 추가
                    Long myParticipantId = projectService.findMyParticipantId(member,project);
                    return ProjectResponse.of(project, myParticipantId, participantDtos);
                }).toList();

        return CustomResponse.success(responses);
    }

    @ResponseBody
    @GetMapping("/projects/{projectId}")
    public CustomResponse<ProjectResponse> findProjectById(
            @PathVariable Long projectId,
            @RequestParam(name = "member-id") Long memberId){
        //TODO Id 암호화 로직 AOP로 변경
        Member member = projectService.findMember(memberId);
        Project project = projectService.findProjectById(projectId);
        List<ParticipantDto> participantDtos = participantService
                .findParticipantsByProject(project).stream()
                .map(ParticipantDto::of)
                .toList();
        Long myParticipantId = projectService.findMyParticipantId(member,project);
        ProjectResponse response = ProjectResponse.of(project, myParticipantId, participantDtos);

        return CustomResponse.success(response);
    }





}
