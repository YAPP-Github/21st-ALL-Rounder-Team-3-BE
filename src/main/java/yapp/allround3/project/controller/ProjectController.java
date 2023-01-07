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
    @GetMapping("")
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
    @GetMapping("/{projectId}")
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

    // "/tasks" api가 여기에 위치하는게 맞을지 생각해봐야 할 것 같음. 여기서 task패키지의 dto, service등에 의존하게 됨 (설계 공부 필요,,)
    @ResponseBody
    @GetMapping("/{projectId}/tasks")
    public CustomResponse<List<TaskResponse.TaskInfo>> findTasksByProject(
            @PathVariable Long projectId,
            @RequestParam(name="participant-id") Long participantId
    ){
        Project project = projectService.findProjectById(projectId);
        int participantCount = projectService.findParticipantCountByProject(project);
        Participant representative = projectService.findParticipantById(participantId);

        List<Task> tasks = taskService.findTaskByParticipant(representative);
        List<TaskResponse.TaskInfo> taskInfos = tasks.stream()
                .map(task ->
                        TaskResponse.TaskInfo.of(task,representative,participantCount))
                .toList();
        return CustomResponse.success(taskInfos);
    }

    @ResponseBody
    @GetMapping("/{projectId}/participants")
    public CustomResponse<List<ParticipantDto>> findParticipantsByProject(
            @PathVariable Long projectId){
        Project project = projectService.findProjectById(projectId);
        List<ParticipantDto> participantDtos = participantService
                .findParticipantsByProject(project).stream()
                .map(ParticipantDto::of)
                .toList();

        return CustomResponse.success(participantDtos);
    }


}
