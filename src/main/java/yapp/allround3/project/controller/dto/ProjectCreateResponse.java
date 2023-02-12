package yapp.allround3.project.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProjectCreateResponse {
    private Long projectId;
    private LocalDateTime createdAt;

    public ProjectCreateResponse(Long projectId, LocalDateTime createdAt){
        this.projectId = projectId;
        this.createdAt = createdAt;
    }

    public static ProjectCreateResponse of(Long projectId, LocalDateTime createdAt){
        return new ProjectCreateResponse(projectId,createdAt);
    }
}
