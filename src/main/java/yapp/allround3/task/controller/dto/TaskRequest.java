package yapp.allround3.task.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import yapp.allround3.task.domain.TaskStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskRequest {
    private Long participantId;
    private String title;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String memo;
    private List<TaskRequest.TaskContentRequest> taskContents;
    private TaskStatus taskStatus;

    @Data
    @NoArgsConstructor
    public static class TaskContentRequest{
        private String title;
        private String url;
    }
}
