package yapp.allround3.project.controller.dto;

import java.time.LocalDate;

import lombok.Data;
import yapp.allround3.project.domain.Difficulty;
import yapp.allround3.project.domain.ProjectStatus;

@Data
public class ProjectRequest {

    private String name;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String goal;
    private Difficulty difficulty;
	private ProjectStatus projectStatus;
}
