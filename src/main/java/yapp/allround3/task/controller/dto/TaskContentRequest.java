package yapp.allround3.task.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskContentRequest {
    private String title;
    private String url;
}
