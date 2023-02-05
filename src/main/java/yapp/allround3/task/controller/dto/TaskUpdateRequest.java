package yapp.allround3.task.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import yapp.allround3.task.domain.TaskStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskUpdateRequest {
    private Long taskId;
    private Long participantId;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private String memo;
    private List<TaskUpdateRequest.TaskContentRequest> taskContents;
    private TaskStatus taskStatus;

    @Data
    @NoArgsConstructor
    public static class TaskContentRequest {
        private Long taskContentId;
        private String title;
        private String url;
    }
}
