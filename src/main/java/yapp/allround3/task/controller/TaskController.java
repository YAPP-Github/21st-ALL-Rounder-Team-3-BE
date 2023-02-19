package yapp.allround3.task.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.interceptor.NoAuth;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;
import yapp.allround3.task.controller.dto.*;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.domain.TaskContent;
import yapp.allround3.task.domain.TaskStatus;
import yapp.allround3.task.service.TaskService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
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
        List<TaskContent> taskContents = taskService.findTaskContentsByTask(task);
        TaskResponse.TaskInfo taskInfo = TaskResponse.TaskInfo.of(task, participant, taskContents);
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
                .status(taskCreateRequest.getTaskStatus()))
                .participant(participant)
                .build();


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
            @RequestParam(name = "participant-id") Long participantId
    ) {
        Project project = projectService.findProjectById(projectId);
        Participant representative = participantService.findParticipantById(participantId);

        List<Task> tasks = taskService.findTaskByParticipant(representative);
        List<TaskResponse.TaskInfo> taskInfos = tasks.stream()
                .map(task ->
                        TaskResponse.TaskInfo.of(task, representative, taskService.findTaskContentsByTask(task)))
                .toList();
        return CustomResponse.success(taskInfos);
    }


}
