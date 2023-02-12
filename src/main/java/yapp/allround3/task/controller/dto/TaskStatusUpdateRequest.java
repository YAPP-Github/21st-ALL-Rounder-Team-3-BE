package yapp.allround3.task.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import yapp.allround3.task.domain.TaskStatus;

@Data
@NoArgsConstructor
public class TaskStatusUpdateRequest {
    private Long taskId;
    private TaskStatus taskStatus;
}
