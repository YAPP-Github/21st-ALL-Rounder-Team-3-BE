package yapp.allround3.project.controller;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.security.SecurityUtils;
import yapp.allround3.member.domain.Member;
import yapp.allround3.participant.controller.dto.ParticipantDto;
import yapp.allround3.project.controller.dto.ProjectResponse;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;


    @ResponseBody
    @GetMapping("")
    public CustomResponse<List<ProjectResponse>> findProjects(@RequestParam Long memberId){
        // TODO : 임시방편으로 memberId받도록 설정했음, 추후에 jwt 로직 추가되면 거기서 꺼내서 쓰도록 할 예정.
        Member member = projectService.findMember(memberId);
        List<Project> projects = projectService.findProjectByMember(member);
        List<ProjectResponse> responses = projects.stream()
                .map(project -> {
                    List<ParticipantDto> participantDtos = projectService
                            .findParticipantsInProject(project).stream()
                            .map(ParticipantDto::of)
                            .toList();
                        return ProjectResponse.of(project, participantDtos);
                }).toList();

        return CustomResponse.success(responses);
    }

    @ResponseBody
    @GetMapping("")
    public CustomResponse<ProjectResponse> findProjects(@PathVariable String projectId){
        //Project id 암호화 String -> Id 변환이 여기서 일어나는게 맞는지 약간 의문임?
        Project project = projectService.findProjectById(SecurityUtils.decodeKey(projectId));
        List<ParticipantDto> participantDtos = projectService
                .findParticipantsInProject(project).stream()
                .map(ParticipantDto::of)
                .toList();
        ProjectResponse response = ProjectResponse.of(project, participantDtos);

        return CustomResponse.success(response);
    }

}
