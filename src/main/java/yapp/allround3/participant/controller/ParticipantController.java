package yapp.allround3.participant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.participant.controller.dto.ParticipantDto;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipantController {

    private ProjectService projectService;
    private ParticipantService participantService;

    @ResponseBody
    @GetMapping("/projects/{projectId}/participants")
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
