package yapp.allround3.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.security.SecurityUtils;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.task.controller.dto.TaskResponse;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final MemberService memberService;
    private final TaskService taskService;
    private final ParticipantService participantService;

    @GetMapping("/{taskId}")
    public CustomResponse<TaskResponse.DetailedTaskInfo> findTaskById(
            @PathVariable String taskId){

        Task task = taskService.findTaskById(SecurityUtils.decodeKey(taskId));
        Participant participant = participantService.findParticipantById(task.getParticipant().getId());
        int participantCount = participantService.findParticipantCountByProject(participant.getProject())-1; //자기 자신 제외
        List <Member> confirmedList = memberService.findMembersGivenFeedback(task);//피드백 줄 때 본인은 줄 수 없도록 막아야 함.


        TaskResponse.DetailedTaskInfo  detailedTaskInfo = TaskResponse.DetailedTaskInfo.of(task,participant,participantCount,confirmedList);
        return CustomResponse.success(detailedTaskInfo);
    }

}
