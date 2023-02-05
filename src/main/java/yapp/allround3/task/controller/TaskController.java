package yapp.allround3.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import yapp.allround3.common.dto.CustomResponse;
import yapp.allround3.common.interceptor.NoAuth;
import yapp.allround3.participant.domain.Participant;
import yapp.allround3.participant.service.ParticipantService;
import yapp.allround3.project.domain.Project;
import yapp.allround3.project.service.ProjectService;
import yapp.allround3.task.controller.dto.TaskRequest;
import yapp.allround3.task.controller.dto.TaskResponse;
import yapp.allround3.task.domain.Task;
import yapp.allround3.task.domain.TaskContent;
import yapp.allround3.task.service.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final ParticipantService participantService;

    @NoAuth
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

    @PostMapping("/tasks")
    public CustomResponse<String> createTask(TaskRequest taskRequest) {
        Participant participant = participantService.findParticipantById(taskRequest.getParticipantId());
        Task task = (Task.builder().
                title(taskRequest.getTitle())
                .dueDate(taskRequest.getDueDate())
                .startDate(taskRequest.getStartDate())
                .memo(taskRequest.getMemo())
                .status(taskRequest.getTaskStatus()))
                .participant(participant)
                .build();

        taskService.saveTask(task);

        List<TaskContent> taskContents = taskRequest.getTaskContents()
                .stream().map(taskContentRequest -> TaskContent.builder()
                        .url(taskContentRequest.getUrl())
                        .task(task)
                        .title(taskContentRequest.getTitle())
                        .build()).toList();

        taskService.saveTaskContents(taskContents);

        return CustomResponse.success("success");
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
        Participant representative = projectService.findParticipantById(participantId);

        List<Task> tasks = taskService.findTaskByParticipant(representative);
        List<TaskResponse.TaskInfo> taskInfos = tasks.stream()
                .map(task ->
                        TaskResponse.TaskInfo.of(task, representative, participantCount, taskService.findTaskContentsByTask(task)))
                .toList();
        return CustomResponse.success(taskInfos);
    }



}
