package yapp.allround3.project.controller.dto;

import java.time.LocalDate;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yapp.allround3.project.domain.Difficulty;
import yapp.allround3.project.domain.ProjectStatus;

@Data
public class ProjectRequest {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private String goal;
    private Difficulty difficulty;
    private ProjectStatus projectStatus;
}
