package yapp.allround3.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.interceptor.NoAuth;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;
import yapp.allround3.task.controller.dto.TaskCreateRequest;
import yapp.allround3.task.controller.dto.TaskResponse;
import yapp.allround3.task.controller.dto.TaskUpdateRequest;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.domain.TaskContent;
import yapp.allround3.task.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TaskController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final ParticipantService participantService;


    @GetMapping("/tasks/{taskId}")
    public CustomResponse<TaskResponse.TaskInfo> findTaskById(
            @PathVariable Long taskId) {

        Task task = taskService.findTaskById(taskId);
        Participant participant = participantService.findParticipantById(task.getParticipant().getId());
        int participantCount = participantService.findParticipantCountByProject(participant.getProject()) - 1; //자기 자신 제외
        List<TaskContent> taskContents = taskService.findTaskContentsByTask(task);
        TaskResponse.TaskInfo taskInfo = TaskResponse.TaskInfo.of(task, participant, participantCount, taskContents);
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
                .status(taskCreateRequest.getTaskStatus()))
                .participant(participant)
                .build();


        taskService.saveTask(task);

        List<TaskContent> taskContents = taskCreateRequest.getTaskContents()
                .stream().map(taskContentRequest -> TaskContent.builder()
                        .url(taskContentRequest.getUrl())
                        .task(task)
                        .title(taskContentRequest.getTitle())
                        .build()).toList();

        taskService.saveTaskContents(taskContents);

        return CustomResponse.success("create task success");
    }

    @PutMapping("/tasks/{taskId}")
    public CustomResponse<String> updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest,
                                             @PathVariable Long taskId) {

        taskService.updateTask(taskUpdateRequest);
        taskUpdateRequest.getTaskContents().forEach(taskService::updateTaskContent);

        return CustomResponse.success("update task success");
    }

    @NoAuth
    @ResponseBody
    @GetMapping("/projects/{projectId}/tasks")
    public CustomResponse<List<TaskResponse.TaskInfo>> findTasksByProject(
            @PathVariable Long projectId,
            @RequestParam(name = "participant-id") Long participantId
    ) {
        Project project = projectService.findProjectById(projectId);
        int participantCount = projectService.findParticipantCountByProject(project);
        Participant representative = participantService.findParticipantById(participantId);

        List<Task> tasks = taskService.findTaskByParticipant(representative);
        List<TaskResponse.TaskInfo> taskInfos = tasks.stream()
                .map(task ->
                        TaskResponse.TaskInfo.of(task, representative, participantCount, taskService.findTaskContentsByTask(task)))
                .toList();
        return CustomResponse.success(taskInfos);
    }


}
