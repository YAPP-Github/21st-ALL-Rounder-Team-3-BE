package yapp.allround3.task.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.interceptor.NoAuth;
import yapp.allround3.feedback.domain.Feedback;
import yapp.allround3.feedback.repository.FeedbackRepository;
import yapp.allround3.member.domain.Member;
import yapp.allround3.member.service.MemberService;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;
import yapp.allround3.task.controller.dto.*;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.domain.TaskContent;
import yapp.allround3.task.domain.TaskStatus;
import yapp.allround3.task.service.TaskService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TaskController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final ParticipantService participantService;
    private final FeedbackRepository feedbackRepository;
    private final MemberService memberService;


    @GetMapping("/tasks/{taskId}")
    public CustomResponse<TaskResponse.TaskInfo> findTaskById(
        HttpServletRequest request,
        @PathVariable Long taskId
    ) {
        Task task = taskService.findTaskById(taskId);
        Participant participant = participantService.findParticipantById(task.getParticipant().getId());
        List<TaskContent> taskContents = taskService.findTaskContentsByTask(task);

        // 피드백 수행 여부 확인
        Long memberId = (Long)request.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);
        Participant RequestedParticipant = participantService.findParticipantByProjectAndMember(
            participant.getProject(),
            member
        );
        Optional<Feedback> optionalFeedback = feedbackRepository.findByTaskAndParticipant(task, RequestedParticipant);
        FeedbackStatus feedbackStatus = FeedbackStatus.PENDING;
        if (optionalFeedback.isPresent()) {
            feedbackStatus = FeedbackStatus.FINISHED;
        }

        TaskResponse.TaskInfo taskInfo = TaskResponse.TaskInfo.of(task, participant, taskContents, feedbackStatus);
        return CustomResponse.success(taskInfo);
    }

    @PostMapping("/projects/{projectId}/tasks")
    public CustomResponse<String> createTask(@PathVariable Long projectId,
                                             @RequestBody TaskCreateRequest taskCreateRequest) {
        Participant participant = participantService.findParticipantById(taskCreateRequest.getParticipantId());
        Task task = (Task.builder().
                title(taskCreateRequest.getTitle())
                .dueDate(taskCreateRequest.getDueDate())
                .startDate(taskCreateRequest.getStartDate())
                .memo(taskCreateRequest.getMemo())
                .feedbackRequiredPersonnel(participantService.findParticipantCountByProjectId(projectId)-1)
                .status(TaskStatus.BEFORE)
                .participant(participant)
                .build()
        );

        taskService.saveTask(task);

        return CustomResponse.success("create task success");
    }

    @PutMapping("/tasks/{taskId}")
    public CustomResponse<String> updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest,
                                             @PathVariable Long taskId) {

        taskService.updateTask(taskUpdateRequest);
        return CustomResponse.success("update task success");
    }

    @PatchMapping("/tasks/{taskId}/status")
    public CustomResponse<String> updateTaskStatus(@RequestBody TaskStatusUpdateRequest taskStatusUpdateRequest,
                                                   @PathVariable Long taskId) {

        taskService.updateTaskStatus(taskId, taskStatusUpdateRequest.getTaskStatus());
        return CustomResponse.success("update task status");
    }

    @PostMapping("/tasks/{taskId}/taskContents")
    public CustomResponse<String> createTaskContent(@RequestBody TaskContentRequest taskContentRequest,
                                                    @PathVariable Long taskId) {

        Task task = taskService.findTaskById(taskId);
        TaskContent taskContent = TaskContent.builder().
                title(taskContentRequest.getTitle()).
                url(taskContentRequest.getUrl()).
                task(task).build();
        taskService.saveTaskContent(taskContent);

        return CustomResponse.success("create task Content success");
    }

    @PutMapping("/tasks/{taskId}/taskContents/{taskContentId}")
    public CustomResponse<String> updateTaskContent(@RequestBody TaskContentRequest taskContentRequest,
                                                    @PathVariable Long taskId,
                                                    @PathVariable Long taskContentId) {

        taskService.updateTaskContent(taskContentId, taskContentRequest);

        return CustomResponse.success("update task Content success");
    }

    @NoAuth
    @ResponseBody
    @GetMapping("/projects/{projectId}/tasks")
    public CustomResponse<List<TaskResponse.TaskInfo>> findTasksByProject(
            @PathVariable Long projectId,
            @RequestParam(name = "participant-id",required = false) Long participantId
    ) {
        if(participantId==null){
            List<TaskResponse.TaskInfo> taskResponses = taskService.getTasksByProject(projectId);
            return CustomResponse.success(taskResponses);
        }
        Project project = projectService.findProjectById(projectId);
        Participant representative = participantService.findParticipantById(participantId);

        List<Task> tasks = taskService.findTaskByParticipant(representative);
        List<TaskResponse.TaskInfo> taskInfos = tasks.stream()
                .map(task ->
                        TaskResponse.TaskInfo.of(
                            task,
                            representative,
                            taskService.findTaskContentsByTask(task),
                            FeedbackStatus.NONE
                        )
                )
                .toList();
        return CustomResponse.success(taskInfos);
    }


}
